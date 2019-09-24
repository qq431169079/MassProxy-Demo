package com.rainng.massproxy.models.dto;

import lombok.Data;

/**
 * 分页信息
 */
@Data
public class PageInfoDTO {
    private Integer itemCount;
    private Integer pageSize;

    public PageInfoDTO() {

    }

    public PageInfoDTO(Integer itemCount, Integer pageSize) {
        this.itemCount = itemCount;
        this.pageSize = pageSize;
    }
}
