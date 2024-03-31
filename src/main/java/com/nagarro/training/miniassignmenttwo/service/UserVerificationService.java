package com.nagarro.training.miniassignmenttwo.service;

import com.nagarro.training.miniassignmenttwo.model.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserVerificationService {


	Flux<User> verifyUser(int size);

}
