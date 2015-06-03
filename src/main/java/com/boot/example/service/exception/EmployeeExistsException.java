package com.boot.example.service.exception;

public class EmployeeExistsException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8955426859975069271L;

	public EmployeeExistsException(final String message) {
        super(message);
    }
}
