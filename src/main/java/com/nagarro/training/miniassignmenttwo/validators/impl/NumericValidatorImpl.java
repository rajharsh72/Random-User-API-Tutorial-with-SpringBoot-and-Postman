package com.nagarro.training.miniassignmenttwo.validators.impl;

import com.nagarro.training.miniassignmenttwo.exceptions.ValidationException;
import com.nagarro.training.miniassignmenttwo.validators.Validator;

/**
 * @author harshraj01
 * Validator class that validates the given number to be whole number and not a 
 * decimal number
 *
 */
public class NumericValidatorImpl implements Validator<Integer> {

	@Override
	public boolean validate(Integer input) throws ValidationException {
		// checks if the integer in null or negative integer
		if(input == null || input<0) {
			throw new ValidationException("Please enter a valid integer input");
		}
		return true;
	}
	
	

}
