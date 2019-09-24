package com.rainng.massproxy.models.dto;

import lombok.Data;

@Data
public class DashboardDTO {
    private Integer taskSize = 0;
    private Integer taskCount = 0;
    private Integer taskSpeed = 0;

    private Integer openSize = 0;
    private Integer openCount = 0;
    private Integer openSpeed = 0;

    private Integer okSize = 0;
    private Integer okCount = 0;
    private Integer okSpeed = 0;

    private Integer crawlSize = 0;
    private Integer crawlCount = 0;
    private Integer crawlSpeed = 0;

    private String taskNext = "";
    private String fastNext = "";
    private String accNext = "";
    private String crawlNext = "";

    public DashboardDTO() {

    }

    public DashboardDTO(Integer taskSize, Integer taskCount, Integer taskSpeed, Integer openSize, Integer openCount,
                        Integer openSpeed, Integer okSize, Integer okCount, Integer okSpeed, Integer crawlSize,
                        Integer crawlCount, Integer crawlSpeed, String taskNext, String fastNext, String accNext,
                        String crawlNext) {
        this.taskSize = taskSize;
        this.taskCount = taskCount;
        this.taskSpeed = taskSpeed;
        this.openSize = openSize;
        this.openCount = openCount;
        this.openSpeed = openSpeed;
        this.okSize = okSize;
        this.okCount = okCount;
        this.okSpeed = okSpeed;
        this.crawlSize = crawlSize;
        this.crawlCount = crawlCount;
        this.crawlSpeed = crawlSpeed;
        this.taskNext = taskNext;
        this.fastNext = fastNext;
        this.accNext = accNext;
        this.crawlNext = crawlNext;
    }
}
