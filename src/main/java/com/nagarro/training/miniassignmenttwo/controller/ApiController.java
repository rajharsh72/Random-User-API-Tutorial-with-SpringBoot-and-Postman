package com.nagarro.training.miniassignmenttwo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.training.miniassignmenttwo.exceptions.ValidationException;
import com.nagarro.training.miniassignmenttwo.model.ApiResponse;
import com.nagarro.training.miniassignmenttwo.model.User;
import com.nagarro.training.miniassignmenttwo.service.FetchUserDataService;
import com.nagarro.training.miniassignmenttwo.service.UserVerificationService;
import com.nagarro.training.miniassignmenttwo.validators.Validator;
import com.nagarro.training.miniassignmenttwo.validators.impl.ValidatorFactory;

import reactor.core.publisher.Flux;

/**
 * @author harshraj01
 * Controller class to handle all the requests made through the api calls.
 *
 */
@RestController
public class ApiController {
	
	@Autowired
	private FetchUserDataService fetchUserDataService;
	

	@Autowired
	private UserVerificationService userVerificationService;
	
	
	/**
	 * 
	 * @param size 
	 * @return a response entity with the list of random users added in the database.
	 */
	@PostMapping("/user")
	public ResponseEntity<Flux<User>> verifyUser(@RequestParam(name="results", defaultValue = "1") String size){
		try {
			int parsedSize = Integer.parseInt(size);
			
			//condition to throw the error if the size value is less than 1 or greater than 5
			if(parsedSize<1 || parsedSize>5) {
				throw new ValidationException("Result size should be an integer between 1 to 5");
			}
			
			//stores a list of random user stored in the database.
			Flux<User> users = this.userVerificationService.verifyUser(parsedSize);
			return ResponseEntity.ok().body(users);
		}catch(NumberFormatException e) {
			throw new ValidationException("Invalid size format. Please enter an integer for size parameter");
		}
	}
	
	
	/**
	 * 
	 * @param sortType to sort the users according to either Name or Age
	 * @param sortOrder to order the set of users according to the lenght of their names i.e. Odd or Even
	 * @param offset defines a value by which we have to skip the result.
	 * @param limit defines how many values to be returned in a single call
	 * @return List of users based on the given order.
	 */
	@GetMapping("/user")
	public ResponseEntity<List<ApiResponse>> fetchUserData(@RequestParam(name="SortType", required=true) String sortType,
									@RequestParam(name="SortOrder", required=true) String sortOrder,
									@RequestParam(name="offset", defaultValue = "0") String offset,
									@RequestParam(name="limit", defaultValue = "5") String limit){
		
		
		Validator<Integer> offsetValidator = (Validator<Integer>) ValidatorFactory.getValidator(Integer.parseInt(offset));
		Validator<Integer> limitValidator = (Validator<Integer>) ValidatorFactory.getValidator(Integer.parseInt(limit));
		Validator<String> sortTypeValidator = (Validator<String>) ValidatorFactory.getValidator(sortType);
		Validator<String> sortOrderValidator = (Validator<String>) ValidatorFactory.getValidator(sortOrder);
		
		int parsedLimit = Integer.parseInt(limit);
		int parsedOffset = Integer.parseInt(offset);
		
		//condition to throw the error if the size value is less than 1 or greater than 5
		if(parsedLimit<1 || parsedLimit>5) {
			throw new ValidationException("Limit should be an integer between 1 to 5");
		}
		
		//apply all the necessary validations on the request parameters.
		if(!offsetValidator.validate(parsedOffset) || !limitValidator.validate(parsedLimit) ||
				!sortTypeValidator.validate(sortType) ||  !sortOrderValidator.validate(sortOrder)) {
			
			throw new ValidationException("Invalid parameter type.");
		}
		
		
		//stores the result fetched from the database.
		List<ApiResponse> results = this.fetchUserDataService.fetchUserData(sortType, sortOrder, parsedOffset, parsedLimit);
		return ResponseEntity.ok().body(results);
	}
	
}
