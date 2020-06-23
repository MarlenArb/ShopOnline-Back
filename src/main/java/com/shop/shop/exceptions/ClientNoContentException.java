package com.shop.shop.exceptions;

import com.shop.shop.exceptions.generic.NoContentException;

public class ClientNoContentException extends NoContentException {
	
	private static final long serialVersionUID = 1L;
	
	public ClientNoContentException(String details) {
		super(details);
	}

}
