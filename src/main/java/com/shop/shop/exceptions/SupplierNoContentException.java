package com.shop.shop.exceptions;

import com.shop.shop.exceptions.generic.NoContentException;

public class SupplierNoContentException extends NoContentException {
	
	private static final long serialVersionUID = 1L;
	
	public SupplierNoContentException(String details) {
		super(details);
	}

}
