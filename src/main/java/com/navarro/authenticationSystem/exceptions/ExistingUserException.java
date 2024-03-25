package com.navarro.authenticationSystem.exceptions;

public class ExistingUserException extends RuntimeException{

    public ExistingUserException(){
        super("User already exists!");
    }

    public ExistingUserException(String message){
        super(message);
    }
}
