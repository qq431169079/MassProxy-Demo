package com.rainng.massproxy.models.bo;

import lombok.Data;

/**
 * 地理位置
 */
@Data
public class LocationBO {
    private String countryCode;
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
    /**
     * 纬度
     */
    private Double latitude;
    /**
     * 经度
     */
    private Double longitude;

    public LocationBO() {

    }

    public LocationBO(String countryCode, String country, String region, String city, Double latitude, Double longitude) {
        this.countryCode = countryCode;
        this.country = country;
        this.region = region;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
