package com.nagarro.training.miniassignmenttwo.service;

import java.util.List;

import reactor.core.publisher.Mono;

public interface NationalityApiService {

	List<String> getNationalities(String name);

}
