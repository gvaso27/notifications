package com.example.notifications.exception;

import lombok.Getter;

@Getter
public class MyException extends RuntimeException {

    private int code;

    public MyException(String message) {
        super(message);
    }

    public MyException(String message, MyErrorCode errorCode) {
        super(message);
        this.code = errorCode.getCode();
    }
}
