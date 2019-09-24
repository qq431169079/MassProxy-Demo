package com.rainng.massproxy.models.bo;

import lombok.Data;

/**
 * 地理位置(Id)
 */
@Data
public class LocationIdBO {
    /**
     * 国家Id
     */
    private Integer countryId;
    /**
     * 省份/区域Id
     */
    private Integer regionId;
    /**
     * 城市Id
     */
    private Integer cityId;

    public LocationIdBO() {

    }

    public LocationIdBO(Integer countryId, Integer regionId, Integer cityId) {
        this.countryId = countryId;
        this.regionId = regionId;
        this.cityId = cityId;
    }
}
