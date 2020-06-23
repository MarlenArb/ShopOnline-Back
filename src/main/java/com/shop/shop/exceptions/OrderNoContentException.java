package com.shop.shop.exceptions;

import com.shop.shop.exceptions.generic.NoContentException;

public class OrderNoContentException extends NoContentException {
	
	private static final long serialVersionUID = 1L;
	
	public OrderNoContentException(String details) {
		super(details);
	}

}