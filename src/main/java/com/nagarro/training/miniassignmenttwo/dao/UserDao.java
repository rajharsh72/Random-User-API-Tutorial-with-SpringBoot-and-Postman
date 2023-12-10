package com.nagarro.training.miniassignmenttwo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.training.miniassignmenttwo.model.User;

import reactor.core.publisher.Mono;

/**
 * @author harshraj01
 * Dao class to perform any operations in the database for the user entity.
 *
 */
@Repository
public interface UserDao extends JpaRepository<User, Long> {


}
