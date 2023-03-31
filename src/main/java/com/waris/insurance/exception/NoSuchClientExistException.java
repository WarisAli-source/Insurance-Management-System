package com.waris.insurance.exception;

public class NoSuchClientExistException extends RuntimeException{
    private String message;

    public NoSuchClientExistException() {}

    public NoSuchClientExistException(String msg)
    {
        super(msg);
        this.message = msg;
    }
}
