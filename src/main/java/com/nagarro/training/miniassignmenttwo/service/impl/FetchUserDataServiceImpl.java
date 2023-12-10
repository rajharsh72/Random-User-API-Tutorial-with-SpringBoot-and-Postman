package com.nagarro.training.miniassignmenttwo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.training.miniassignmenttwo.dao.UserDao;
import com.nagarro.training.miniassignmenttwo.model.ApiResponse;
import com.nagarro.training.miniassignmenttwo.model.PageInfo;
import com.nagarro.training.miniassignmenttwo.model.User;
import com.nagarro.training.miniassignmenttwo.service.FetchUserDataService;
import com.nagarro.training.miniassignmenttwo.service.SortUserService;

/**
 * @author harshraj01
 * Service class that fetches the user data from the database 
 * and then sorts them, apply limit and offset 
 * and then return to the controller.
 */
@Service
public class FetchUserDataServiceImpl implements FetchUserDataService {
	
	@Autowired 
	private UserDao userDao;
	
	@Autowired
	private SortUserService sortUserService;
	
	@Override
	public List<ApiResponse> fetchUserData(String sortType, String sortOrder, int offset, int limit){
		
		//fetch all the user data from the database
		List<User> allUsers = this.userDao.findAll();
		
		List<User> filteredUserList = allUsers.stream()
				.skip(offset)
				.limit(limit)
				.collect(Collectors.toList());
		
		List<User> sortedUserList = new ArrayList<>();
		
		
		
		
		//sorting based on Name and ODD
		if("Name".equalsIgnoreCase(sortType) && "Odd".equalsIgnoreCase(sortOrder)) {
			sortedUserList.addAll(this.sortUserService.basedOnNameAndOdd(filteredUserList));
			sortedUserList.addAll(this.sortUserService.basedOnNameAndEven(filteredUserList));
		}
		
		//sorting based on Name and EVEN
		else if("Name".equalsIgnoreCase(sortType) && "Even".equalsIgnoreCase(sortOrder)) {
			sortedUserList.addAll(this.sortUserService.basedOnNameAndEven(filteredUserList));
			sortedUserList.addAll(this.sortUserService.basedOnNameAndOdd(filteredUserList));
		}
		
		//sorting based on Age and ODD
		else if("Age".equalsIgnoreCase(sortType) && "Odd".equalsIgnoreCase(sortOrder)) {
			sortedUserList.addAll(this.sortUserService.basedOnAgeAndOdd(filteredUserList));
			sortedUserList.addAll(this.sortUserService.basedOnAgeAndEven(filteredUserList));
		}
		
		//sorting based on Age and EVEN
		else if("Age".equalsIgnoreCase(sortType) && "Even".equalsIgnoreCase(sortOrder)) {
			sortedUserList.addAll(this.sortUserService.basedOnAgeAndEven(filteredUserList));
			sortedUserList.addAll(this.sortUserService.basedOnAgeAndOdd(filteredUserList));
		}
		else {
			throw new IllegalArgumentException("Please enter a valid SortType(Name or Age) or SortOrder(Even or Odd)");
		}
		
		
		//count the total number of users in the database
		int totalUsers = allUsers.size();
		
		//checks if there are more users.
		boolean hasNext = (offset+limit)<totalUsers;
		
		//checks if there was any previous user data in the previous page
		boolean hasPrevious = offset>0;
		
		List<ApiResponse> apiResponse = sortedUserList.stream()
											.map(user ->{
												
												PageInfo pageInfo = new PageInfo(hasNext, hasPrevious, totalUsers);
												ApiResponse responseData = new ApiResponse();
												responseData.setUserData(user);
												responseData.setPageInfo(pageInfo);
												
												
												return responseData;
											}).collect(Collectors.toList());
		
		return apiResponse;
	}

}
