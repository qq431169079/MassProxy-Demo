package com.rainng.massproxy.models.exception;

public class EntityNotFoundException extends BaseException {
    public EntityNotFoundException() {
        super("实体未找到");
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
