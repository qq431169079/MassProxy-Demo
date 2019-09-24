package com.rainng.massproxy.models.exception;

public class ArgumentException extends BaseException {
    public ArgumentException() {
        super("参数错误");
    }

    public ArgumentException(String message) {
        super(message);
    }
}
