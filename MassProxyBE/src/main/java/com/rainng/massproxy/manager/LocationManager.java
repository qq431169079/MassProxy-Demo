package com.rainng.massproxy.manager;

import com.rainng.massproxy.dao.*;
import com.rainng.massproxy.models.bo.LocationBO;
import com.rainng.massproxy.models.dto.IpDetailsDTO;
import com.rainng.massproxy.models.entity.*;
import com.rainng.massproxy.models.exception.EntityNotFoundException;
import com.rainng.massproxy.models.exception.InvalidIpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LocationManager extends BaseManager {
    @Autowired
    private CountryDAO countryDAO;
    @Autowired
    private RegionDAO regionDAO;
    @Autowired
    private CityDAO cityDAO;
    @Autowired
    private RangeDAO rangeDAO;
    @Autowired
    private ProxyDAO proxyDAO;

    public IpDetailsDTO getIpDetails(String ip) throws InvalidIpException {
        LocationBO location = getLocationByIp(ip);
        ProxyEntity proxy = proxyDAO.getByIp(ip);

        return IpDetailsDTO.from(ip, location, proxy);
    }

    /**
     * 根据Ip获取地理位置
     *
     * @param ip Ip
     * @return 地理位置
     */
    public LocationBO getLocationByIp(String ip) throws InvalidIpException {
        long ipCode = convertIpToCode(ip);
        RangeEntity range = rangeDAO.select(ipCode);
        CityEntity city = cityDAO.getCityById(range.getCityId());
        RegionEntity region = regionDAO.getRegionById(city.getRegionId());
        CountryEntity country = countryDAO.getCountryById(region.getCountryId());

        return new LocationBO(country.getCode(), country.getName(), region.getName(), city.getName(), range.getLatitude(), range.getLongitude());
    }

    public List<String> listAvailableCountry() {
        List<String> countryList = new ArrayList<>();
        List<ProxyEntity> proxyList = proxyDAO.listCountryId();

        for (ProxyEntity proxy : proxyList) {
            String name = countryDAO.getNameById(proxy.getCountryId());
            countryList.add(name);
        }

        return countryList;
    }

    public List<String> listAvailableRegion(String country) throws EntityNotFoundException {
        Integer countryId = countryDAO.getIdByName(country);

        List<String> regionList = new ArrayList<>();
        List<ProxyEntity> proxyList = proxyDAO.listRegionIdByCountryId(countryId);

        for (ProxyEntity proxy : proxyList) {
            String name = regionDAO.getNameById(proxy.getRegionId());
            regionList.add(name);
        }

        return regionList;
    }

    /**
     * 将Ip转换为Ip码
     *
     * @param ip Ip
     * @return Ip码
     */
    public long convertIpToCode(String ip) throws InvalidIpException {
        try {
            long code = 0;
            String[] split = ip.split("\\.");
            for (int i = 0; i < 4; i++) {
                code += Integer.valueOf(split[i]) * Math.pow(256, 3 - i);
            }

            return code;
        } catch (Exception ex) {
            throw new InvalidIpException();
        }
    }
}
