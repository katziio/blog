package com.mountblue.blog.exception;

public class UnAuthorizedAccessException extends RuntimeException{
    public UnAuthorizedAccessException(String message) {
        super(message);
    }
}
