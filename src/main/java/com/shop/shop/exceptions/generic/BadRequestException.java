package com.shop.shop.exceptions.generic;

public class BadRequestException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public BadRequestException(String details) {
		super(details);
	}

}