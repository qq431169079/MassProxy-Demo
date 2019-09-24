package com.rainng.massproxy.models.exception;

public class InvalidIpException extends BaseException {
    public InvalidIpException() {
        super("IP格式错误");
    }

    public InvalidIpException(String message) {
        super(message);
    }
}
