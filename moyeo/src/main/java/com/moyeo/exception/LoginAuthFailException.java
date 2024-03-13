package com.moyeo.exception;

import lombok.Getter;

public class LoginAuthFailException extends Exception {
    private static final long serialVersionUID = 1L;

    @Getter
    private String userid;
    
    public LoginAuthFailException(String message) {
        super(message);
    }
    
    public LoginAuthFailException(String message, String userid) {
        super(message);
        this.userid = userid;
    }
}