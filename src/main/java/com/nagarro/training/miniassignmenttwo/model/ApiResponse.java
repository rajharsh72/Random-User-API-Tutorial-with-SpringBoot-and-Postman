package com.nagarro.training.miniassignmenttwo.model;

public class ApiResponse {
	
	private User userData;
	private PageInfo pageInfo;
	
	public ApiResponse() {
		
	}
	
	
	/**
	 * @param userData
	 * @param pageInfo
	 */
	public ApiResponse(User userData, PageInfo pageInfo) {
		super();
		this.userData = userData;
		this.pageInfo = pageInfo;
	}
	
	public User getUserData() {
		return userData;
	}
	
	public void setUserData(User userData) {
		this.userData = userData;
	}
	
	public PageInfo getPageInfo() {
		return pageInfo;
	}
	
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
	
	@Override
	public String toString() {
		return "ApiResponse [userData=" + userData + ", pageInfo=" + pageInfo + "]";
	}
	
	
}
