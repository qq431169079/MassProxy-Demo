package com.rainng.massproxyscan.manager;

import com.rainng.massproxyscan.dao.CityDAO;
import com.rainng.massproxyscan.dao.CountryDAO;
import com.rainng.massproxyscan.dao.RangeDAO;
import com.rainng.massproxyscan.dao.RegionDAO;
import com.rainng.massproxyscan.models.bo.LocationBO;
import com.rainng.massproxyscan.models.bo.LocationIdBO;
import com.rainng.massproxyscan.models.entity.CityEntity;
import com.rainng.massproxyscan.models.entity.CountryEntity;
import com.rainng.massproxyscan.models.entity.RangeEntity;
import com.rainng.massproxyscan.models.entity.RegionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 地理位置Manager
 */
@Component
public class LocationManager {
    @Autowired
    private CountryDAO countryDAO;
    @Autowired
    private RegionDAO regionDAO;
    @Autowired
    private CityDAO cityDAO;
    @Autowired
    private RangeDAO rangeDAO;

    /**
     * 根据Ip获取地理位置Id
     *
     * @param ip Ip
     * @return 地理位置Id
     */
    public LocationIdBO getLocationIdByIp(String ip) {
        long ipCode = convertIpToCode(ip);
        RangeEntity range = rangeDAO.getByIpCode(ipCode);
        CityEntity city = cityDAO.getById(range.getCityId());
        RegionEntity region = regionDAO.getById(city.getRegionId());

        return new LocationIdBO(region.getCountryId(), city.getRegionId(), range.getCityId());
    }

    /**
     * 将Ip转换为Ip码
     *
     * @param ip Ip
     * @return Ip码
     */
    public long convertIpToCode(String ip) {
        long code = 0;
        String[] split = ip.split("\\.");
        for (int i = 0; i < 4; i++) {
            code += Integer.valueOf(split[i]) * Math.pow(256, 3 - i);
        }

        return code;
    }
}
