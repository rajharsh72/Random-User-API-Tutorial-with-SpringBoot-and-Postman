package com.nagarro.training.miniassignmenttwo.validators.impl;

import com.nagarro.training.miniassignmenttwo.exceptions.ValidationException;
import com.nagarro.training.miniassignmenttwo.validators.Validator;

/**
 * @author harshraj01
 * Validator class that validates the string consisting of only english alphabet characters
 *
 */
public class EnglishAlphabetsValidatorImpl implements Validator<String> {

	@Override
	public boolean validate(String input) throws ValidationException {
		// checks if the string is null or any other character rather than alphabet.
		if( input == null || !input.matches("[a-zA-Z]+")) {
			throw new ValidationException("Name should contain only alphabetic characters");
		}
		return true;
	}

	
}
