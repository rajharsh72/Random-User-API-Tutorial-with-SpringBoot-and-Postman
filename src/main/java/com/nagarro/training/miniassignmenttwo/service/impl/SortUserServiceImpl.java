package com.nagarro.training.miniassignmenttwo.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nagarro.training.miniassignmenttwo.model.User;
import com.nagarro.training.miniassignmenttwo.service.SortUserService;

/**
 * @author harshraj01
 * Service class that sorts the list of users based on different parameters provided. 
 *
 */
@Service
public class SortUserServiceImpl implements SortUserService{
	
	
	//sorting based on Name type and Odd order
	
	@Override
	public List<User> basedOnNameAndOdd(List<User> userList){
		List<User> result = userList.stream()
									.filter(user -> (user.getName().length() - countWhiteSpaceCharacters(user.getName())) %2 !=0)
									.sorted(Comparator.comparing(user -> user.getName().length() - countWhiteSpaceCharacters(user.getName())))
									.collect(Collectors.toList());
		return result;
	}
	
	//sorting based on Name type and Even order
	
	@Override
	public List<User> basedOnNameAndEven(List<User> userList){
		List<User> result = userList.stream()
									.filter(user -> (user.getName().length() - countWhiteSpaceCharacters(user.getName())) %2 ==0)
									.sorted(Comparator.comparing(user -> (user.getName().length() - countWhiteSpaceCharacters(user.getName()))))
									.collect(Collectors.toList());

		return result;
	}
	
	//sorting based on Age type and Odd order
	
	@Override
	public List<User> basedOnAgeAndOdd(List<User> userList){
		List<User> result = userList.stream()
									.filter(user -> user.getAge() %2 !=0)
									.sorted(Comparator.comparing(User::getAge))
									.collect(Collectors.toList());
		
		return result;
	}
	
	
	//sorting based on Age type and Even order
	
	@Override
	public List<User> basedOnAgeAndEven(List<User> userList){
		List<User> result = userList.stream()
									.filter(user -> user.getAge() %2 ==0)
									.sorted(Comparator.comparing(User::getAge))
									.collect(Collectors.toList());
		
		return result;
	}
	
	
	
	//method to count the white space characters in a name.
	private int countWhiteSpaceCharacters(String name) {
		int count=0;
		for(char letter : name.toCharArray()) {
			if(Character.isWhitespace(letter)) {
				count++;
			}
		}
		return count;
	}
	

}
