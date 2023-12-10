package com.nagarro.training.miniassignmenttwo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.nagarro.training.miniassignmenttwo.service.GenderApiService;

/**
 * @author harshraj01
 * Service class that makes call to an API using WebClient and then returns a 
 * String value of the gender for a particular user.
 *
 */
@Service
public class GenderApiServiceImpl implements GenderApiService{
	
	@Autowired
	@Qualifier(("api3WebClient"))
	private WebClient api3WebClient;
	
	@Override
	public String getGender(String name){
		System.out.println("API3 is running");
		return api3WebClient.get()
						.uri("?name={name}", name)
						.retrieve()
						.bodyToMono(JsonNode.class)
						.map(jsonNode -> jsonNode.get("gender").asText())
						.block();
		
	}
		
	

}
