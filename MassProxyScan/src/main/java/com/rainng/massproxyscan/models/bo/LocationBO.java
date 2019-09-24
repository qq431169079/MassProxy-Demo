package com.rainng.massproxyscan.models.bo;

import lombok.Data;

/**
 * 地理位置
 */
@Data
public class LocationBO {
    /**
     * 国家
     */
    private String country;
    /**
     * 省份/区域
     */
    private String region;
    /**
     * 城市
     */
    private String city;

    public LocationBO() {

    }

    public LocationBO(String country, String region, String city) {
        this.country = country;
        this.region = region;
        this.city = city;
    }
}
