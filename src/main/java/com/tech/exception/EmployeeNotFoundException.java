package com.tech.exception;

public class EmployeeNotFoundException extends Exception {
    public EmployeeNotFoundException(String message){
        super(message);
    }
}
