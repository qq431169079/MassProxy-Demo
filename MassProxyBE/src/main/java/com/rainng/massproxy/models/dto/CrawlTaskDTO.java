package com.rainng.massproxy.models.dto;

import lombok.Data;

/**
 * 爬虫任务
 */
@Data
public class CrawlTaskDTO {
    private String url;
    private Integer start;
    private Integer end;
}
