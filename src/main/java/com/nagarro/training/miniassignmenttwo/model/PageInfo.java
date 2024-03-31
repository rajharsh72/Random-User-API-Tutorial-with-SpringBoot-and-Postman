package com.nagarro.training.miniassignmenttwo.model;

public class PageInfo {
	
	private boolean hasNext;
	private boolean hasPrevious;
	private int total;

	
	public PageInfo() {
	}


	/**
	 * @param hasNext
	 * @param pasPrevious
	 * @param total
	 */
	public PageInfo(boolean hasNext, boolean hasPrevious, int total) {
		this.hasNext = hasNext;
		this.hasPrevious = hasPrevious;
		this.total = total;
	}


	public boolean isHasNext() {
		return hasNext;
	}


	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}


	public boolean isHasPrevious() {
		return hasPrevious;
	}


	public void setHasPrevious(boolean hasPrevious) {
		this.hasPrevious = hasPrevious;
	}


	public int getTotal() {
		return total;
	}


	public void setTotal(int total) {
		this.total = total;
	}


}
