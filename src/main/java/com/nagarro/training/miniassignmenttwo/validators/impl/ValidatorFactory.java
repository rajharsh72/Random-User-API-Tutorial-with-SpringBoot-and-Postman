package com.nagarro.training.miniassignmenttwo.validators.impl;

import org.springframework.stereotype.Component;

import com.nagarro.training.miniassignmenttwo.validators.Validator;

/**
 * @author harshraj01
 * 
 * Factory Class that returns the appropriate Validator function based 
 * on the instance of value passed.
 */
@Component
public class ValidatorFactory {

	private static final Validator<Integer> numericValidator = new NumericValidatorImpl();
	private static final Validator<String> alphabetsValidator = new EnglishAlphabetsValidatorImpl();

	// Factory method to get the appropriate validator based on the input type
	public static Validator<?> getValidator(Object input) {

		//check if the input is of type integer
		if (input instanceof Integer) {

			return numericValidator;

		}
		//checks if the input is of String type
		else if (input instanceof String) {

			return alphabetsValidator;

		}
		//else throw an illegal argument exception
		else {

			throw new IllegalArgumentException("Unsupported input type");
		}
	}
}
