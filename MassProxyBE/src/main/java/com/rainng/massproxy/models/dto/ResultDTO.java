package com.rainng.massproxy.models.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 响应包装
 */
@Data
public class ResultDTO implements Serializable {
    public static final int SUCCESS = 0;
    public static final int FAIL = 1;
    private static final long serialVersionUID = 1L;
    private Integer code;
    private String message;
    private Object data;

    public ResultDTO() {

    }

    public ResultDTO(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
