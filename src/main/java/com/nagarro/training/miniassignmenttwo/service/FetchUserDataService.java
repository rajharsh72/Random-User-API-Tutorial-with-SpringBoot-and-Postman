package com.nagarro.training.miniassignmenttwo.service;

import java.util.List;

import com.nagarro.training.miniassignmenttwo.model.ApiResponse;
import com.nagarro.training.miniassignmenttwo.model.User;

public interface FetchUserDataService {


	List<ApiResponse> fetchUserData(String sortType, String sortOrder, int offset, int limit);

}
