package com.rainng.massproxy.models.dto;

import lombok.Data;

/**
 * 国家代理计数
 */
@Data
public class ProxyCountryCountDTO {
    private String name;
    private Integer value;

    public ProxyCountryCountDTO() {

    }

    public ProxyCountryCountDTO(String name, Integer value) {
        this.name = name;
        this.value = value;
    }
}
