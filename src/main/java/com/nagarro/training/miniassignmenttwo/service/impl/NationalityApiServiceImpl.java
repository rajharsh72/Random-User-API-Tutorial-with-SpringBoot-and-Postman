package com.nagarro.training.miniassignmenttwo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.nagarro.training.miniassignmenttwo.service.NationalityApiService;

/**
 * @author harshraj01
 * Service class that makes call to an API using WebClient and then returns a 
 * List of the nationalities for a particular user.
 */
@Service
public class NationalityApiServiceImpl implements NationalityApiService {
	
	@Autowired
	@Qualifier("api2WebClient")
	private WebClient api2WebClient;
	

	@Override
	public List<String> getNationalities(String name){
		System.out.println("API2 is running");
		return api2WebClient.get()
						.uri("?name={name}", name)
						.retrieve()
						.bodyToMono(JsonNode.class)
						.map(jsonNode ->{
							List<String> nationalities = new ArrayList<>();
							JsonNode countries = jsonNode.get("country");
							if(countries != null && countries.isArray()) {
								countries.elements().forEachRemaining(country ->{
									String countryId = country.get("country_id").asText();
									nationalities.add(countryId);
								});
							}
							return nationalities;
						}).block();
	}

}
