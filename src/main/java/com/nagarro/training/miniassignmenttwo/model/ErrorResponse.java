package com.nagarro.training.miniassignmenttwo.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ErrorResponse {
	
	private int errorCode;
	private String errorMessage;
	private String errorTimestamp;
	
	
	public ErrorResponse() {
	}


	/**
	 * @param errorCode
	 * @param errorMessage
	 * @param errorTimestamp
	 */
	public ErrorResponse(int errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date currentDate = new Date();
		this.errorTimestamp = dateFormat.format(currentDate);
		
	}
	
	
	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}
	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	/**
	 * @return the errorTimestamp
	 */
	public String getErrorTimestamp() {
		return errorTimestamp;
	}
	/**
	 * @param errorTimestamp the errorTimestamp to set
	 */
	public void setErrorTimestamp(String errorTimestamp) {
		this.errorTimestamp = errorTimestamp;
	}
	
	

}
