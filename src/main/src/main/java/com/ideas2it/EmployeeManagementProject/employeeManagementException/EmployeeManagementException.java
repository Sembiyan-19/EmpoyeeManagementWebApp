package com.ideas2it.EmployeeManagementProject.employeeManagementException;

/**
 * User defined exception
 * @author Sembiyan
 * @version 2.1   May 08
 */
public class EmployeeManagementException extends Exception {

	public EmployeeManagementException(String message) {
		super(message);
	}
	
	public EmployeeManagementException(String message, Throwable cause) {
		super(message);
	}
	
	public EmployeeManagementException(Throwable cause) {
		super(cause);
	}
	
	public EmployeeManagementException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
		super(message);
	}
}
