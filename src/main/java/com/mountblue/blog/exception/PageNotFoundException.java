package com.mountblue.blog.exception;

public class PageNotFoundException  extends RuntimeException{
    public PageNotFoundException(String message) {
        super(message);
    }

}
