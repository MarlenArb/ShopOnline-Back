package com.shop.shop.exceptions;

import com.shop.shop.exceptions.generic.NoContentException;

public class ShopNoContentException extends NoContentException {
	
	private static final long serialVersionUID = 1L;
	
	public ShopNoContentException(String details) {
		super(details);
	}

}
