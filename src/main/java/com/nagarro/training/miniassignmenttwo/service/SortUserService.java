package com.nagarro.training.miniassignmenttwo.service;

import java.util.List;

import com.nagarro.training.miniassignmenttwo.model.User;

public interface SortUserService {

	List<User> basedOnNameAndOdd(List<User> userList);

	List<User> basedOnNameAndEven(List<User> userList);

	List<User> basedOnAgeAndOdd(List<User> userList);

	List<User> basedOnAgeAndEven(List<User> userList);

}
