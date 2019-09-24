package com.rainng.massproxy.models.exception;

public class BaseException extends Exception {
    public BaseException() {
        super("内部错误");
    }

    public BaseException(String message) {
        super(message);
    }
}
