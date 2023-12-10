package com.nagarro.training.miniassignmenttwo.validators;

import com.nagarro.training.miniassignmenttwo.exceptions.ValidationException;

/**
 * @author harshraj01
 * 
 * @param <T>
 */
public interface Validator<T> {

	boolean validate(T input) throws ValidationException;
}
