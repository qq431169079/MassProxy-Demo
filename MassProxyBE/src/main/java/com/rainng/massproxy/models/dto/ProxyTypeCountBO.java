package com.rainng.massproxy.models.dto;

import lombok.Data;

/**
 * 代理分类计数
 */
@Data
public class ProxyTypeCountBO {
    private Integer http;
    private Integer https;
    private Integer both;

    public ProxyTypeCountBO() {

    }

    public ProxyTypeCountBO(Integer http, Integer https, Integer both) {
        this.http = http + both;
        this.https = https + both;
        this.both = both;
    }
}
