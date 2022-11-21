package org.abhishek.customerapi.exception;

public class CustomerAPIInvalidInputException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CustomerAPIInvalidInputException(String message) {
		super(message);
	}
}
