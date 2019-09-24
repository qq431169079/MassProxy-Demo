package com.rainng.massproxy.service;

import com.rainng.massproxy.models.dto.ResultDTO;

/**
 * 服务基类
 */
public class BaseService {
    /**
     * 构造数据传递DTO
     *
     * @param data 数据
     * @return ResultDTO
     */
    protected ResultDTO result(Object data) {
        return new ResultDTO(ResultDTO.SUCCESS, "success", data);
    }

    /**
     * 构造数据传递DTO
     *
     * @param data    数据
     * @param message 消息
     * @return ResultDTO
     */
    protected ResultDTO result(Object data, String message) {
        return new ResultDTO(ResultDTO.SUCCESS, message, data);
    }

    /**
     * 构造失败数据传递DTO
     *
     * @param message 消息
     * @return ResultDTO
     */
    protected ResultDTO failedResult(String message) {
        return new ResultDTO(ResultDTO.FAIL, message, null);
    }

    /**
     * 构造失败数据传递DTO
     *
     * @param message 消息
     * @param data    数据
     * @return ResultDTO
     */
    protected ResultDTO failedResult(String message, Object data) {
        return new ResultDTO(ResultDTO.FAIL, message, data);
    }

    protected boolean isNullOrEmpty(String str) {
        return str == null || "".equals(str);
    }
}
