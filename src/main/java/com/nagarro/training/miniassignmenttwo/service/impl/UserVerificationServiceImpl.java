package com.nagarro.training.miniassignmenttwo.service.impl;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.nagarro.training.miniassignmenttwo.dao.UserDao;
import com.nagarro.training.miniassignmenttwo.model.User;
import com.nagarro.training.miniassignmenttwo.service.GenderApiService;
import com.nagarro.training.miniassignmenttwo.service.NationalityApiService;
import com.nagarro.training.miniassignmenttwo.service.UserVerificationService;

import reactor.core.publisher.Flux;

/**
 * @author harshraj01
 * Service class that verifies the random user by fetching it from remote api
 * using web client services and then verifies the nationality and gender of that user
 * with the two other remote api and all this is done on parallel threads
 *
 */
@Service
public class UserVerificationServiceImpl implements UserVerificationService {
	

	 
	 @Autowired
	 private UserDao userDao;
	 
	 //defines a thread pool of size 2
	 ExecutorService executorService = Executors.newFixedThreadPool(2);
	 
	 @Autowired
	 private NationalityApiService nationalityService;
	 
	 @Autowired
	 private GenderApiService genderService;
	 
	 @Autowired
	 @Qualifier("api1WebClient")
	 private WebClient api1WebClient;

	 private Boolean markVerificationStatus(User user, String gender, List<String> nationalities) {
		
		 if(nationalities.contains(user.getNationality()) && user.getGender().equals(gender)){
			 return true; 
		 }
		return false;
		 
	 }

	 
	 
	@Override
	public Flux<User> verifyUser(int size) {
		// TODO Auto-generated method stub
		
		return api1WebClient.get()
				.uri("?results={size}", size)
				.retrieve()
				.bodyToMono(JsonNode.class)
				.flatMapMany(results -> Flux.fromIterable(results.path("results")))
				.flatMap(result ->{
					System.out.println("API1 is running" + size);
					String gender = result.get("gender").asText();
					String nationality = result.get("nat").asText();
					String firstName = result.path("name").get("first").asText();
					String lastName = result.path("name").get("last").asText();
					int age = Integer.parseInt(result.path("dob").get("age").asText());
					String dob = result.path("dob").get("date").asText();
					String name = firstName+" "+lastName;
					System.out.println(name);
					User user = new User(gender, nationality, name,age, dob);
					
					//fetch the list of nationalities using api2 for a particular user
					CompletableFuture<List<String>> nationalitiesFuture = CompletableFuture.supplyAsync(() -> 
																				this.nationalityService.getNationalities(firstName), executorService);

					//fetch the gender using the api3 for a particular user
					CompletableFuture<String> genderFuture = CompletableFuture.supplyAsync(() -> 
																	this.genderService.getGender(firstName), executorService);
					
					
					CompletableFuture.allOf(genderFuture, nationalitiesFuture).join();
					
					try {
						String genderApi = genderFuture.get();
						List<String> nationalitiesApi = nationalitiesFuture.get();
						
						/*verifies the current nationality and gender from the fetched data 
						using the other two api's*/
						if(markVerificationStatus(user, genderApi, nationalitiesApi)) {
							user.setVerificationStatus("VERIFIED");
						}else {
							user.setVerificationStatus("TO_BE_VERIFIED");
						}
						//saves the data in the database
						this.userDao.save(user);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch(AsyncRequestTimeoutException e) {
						throw new AsyncRequestTimeoutException();
					}

					return Flux.just(user);
				});
	}
}
