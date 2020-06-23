package com.shop.shop.exceptions;

import com.shop.shop.exceptions.generic.NoContentException;

public class ProductNoContentException extends NoContentException {
	
	private static final long serialVersionUID = 1L;
	
	public ProductNoContentException(String details) {
		super(details);
	}

}
