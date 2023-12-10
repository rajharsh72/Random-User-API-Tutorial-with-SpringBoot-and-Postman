package com.nagarro.training.miniassignmenttwo.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nagarro.training.miniassignmenttwo.MiniAssignmentTwoApplication;
import com.nagarro.training.miniassignmenttwo.exceptions.ValidationException;
import com.nagarro.training.miniassignmenttwo.model.ApiResponse;
import com.nagarro.training.miniassignmenttwo.model.User;
import com.nagarro.training.miniassignmenttwo.service.FetchUserDataService;
import com.nagarro.training.miniassignmenttwo.service.UserVerificationService;

import reactor.core.publisher.Flux;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = MiniAssignmentTwoApplication.class)
public class ApiControllerTest {

	@Mock
    private UserVerificationService userVerificationService;
	
	@Mock
	private FetchUserDataService fetchUserDataService;
	
    @InjectMocks
    private ApiController apiController;
    
    
    /**
     * Tests the POST request of the ApiController that returns a List<User>
     * as a response entity with a OK as response status
     */
    @Test
    public void testVerifyUser_ValidSize_ReturnsUsersFlux() {
    	int validSize = 4;

        Mockito.when(userVerificationService.verifyUser(validSize)).thenReturn(Flux.just(new User()));

        ResponseEntity<Flux<User>> response = apiController.verifyUser(String.valueOf(validSize));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    
    
    /**
     * Tests the POST request of the ApiController for the invalid size parameter as it should be
     * not more than 5
     */
    @Test
    public void testVerifyUser_InvalidSize_ThrowsValidationException() {
        int invalidSize = 10;
        try {
            apiController.verifyUser(String.valueOf(invalidSize));
        } catch (ValidationException e) {
            // Expected behavior: ValidationException should be thrown
            assertEquals("Result size should be an integer between 1 to 5", e.getMessage());
        }
    }
    
    
    
    /**
     * Tests the GET request of ApiController that returns the list of users with page info
     * based on the entered parameters.
     */
    @Test
    public void testFetchUserData_ValidParameters_ReturnsListApiResponse() {
    	String sortType = "Name";
    	String sortOrder = "Age";
    	String offset = "4";
    	String limit = "5";
    	
    	List<ApiResponse> mockResponse = Arrays.asList(new ApiResponse(), new ApiResponse());
        Mockito.when(fetchUserDataService.fetchUserData(sortType, sortOrder, 4, 5)).thenReturn(mockResponse);

        ResponseEntity<List<ApiResponse>> response = apiController.fetchUserData(sortType, sortOrder, offset, limit);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
    
    
    /**
     * Tests the GET request of ApiController for the invalid limit parameter as it should be
     * not more than 5 else throw an exception
     */
    @Test
    public void testFetchUserData_InvalidLimitParam_ThrowsValidationException() {
    	String sortType = "Name";
    	String sortOrder = "Age";
    	String offset = "6";
    	String limit = "10";
    	
    	try {
    		apiController.fetchUserData(sortType, sortOrder, offset, limit);
    	}catch(ValidationException e) {
    		assertEquals("Limit should be an integer between 1 to 5", e.getMessage());
    	}
    }
    
    
    /**
     * Tests the GET request of ApiController for the invalid sorttype parameter as it should be
     * an alphabet else return invalid exception
     */
    
    @Test
    public void testFetchUserData_InvalidSortTypeParam_ThrowsValidationException() {
    	String sortType = "9";
    	String sortOrder = "Age";
    	String offset = "6";
    	String limit = "5";
    	
    	try {
    		apiController.fetchUserData(sortType, sortOrder, offset, limit);
    	}catch(ValidationException e) {
    		assertEquals("Name should contain only alphabetic characters", e.getMessage());
    	}
    }
}
