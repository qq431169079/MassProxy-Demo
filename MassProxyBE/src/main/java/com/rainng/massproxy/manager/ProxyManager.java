package com.rainng.massproxy.manager;

import com.rainng.massproxy.dao.CityDAO;
import com.rainng.massproxy.dao.CountryDAO;
import com.rainng.massproxy.dao.ProxyDAO;
import com.rainng.massproxy.dao.RegionDAO;
import com.rainng.massproxy.models.bo.LocationBO;
import com.rainng.massproxy.models.dto.IpDetailsDTO;
import com.rainng.massproxy.models.dto.PageInfoDTO;
import com.rainng.massproxy.models.dto.ProxyDTO;
import com.rainng.massproxy.models.dto.ProxyTypeCountBO;
import com.rainng.massproxy.models.entity.CountryEntity;
import com.rainng.massproxy.models.entity.ProxyEntity;
import com.rainng.massproxy.models.exception.EntityNotFoundException;
import com.rainng.massproxy.models.exception.InvalidIpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProxyManager extends BaseManager {
    @Autowired
    private CountryDAO countryDAO;
    @Autowired
    private RegionDAO regionDAO;
    @Autowired
    private CityDAO cityDAO;
    @Autowired
    private ProxyDAO proxyDAO;
    @Autowired
    private LocationManager locationManager;

    public List<ProxyDTO> query(
            Integer pageIndex,
            Integer port, Integer type, String country,
            Integer maxConnTime, Integer maxRespTime, Integer minScore, Integer order) throws EntityNotFoundException {

        Integer countryId = null;
        if (country != null) {
            countryId = countryDAO.getIdByName(country);
        }

        List<ProxyEntity> proxyList = proxyDAO.query(pageIndex, port, type, countryId, maxConnTime, maxRespTime, minScore, order);
        List<ProxyDTO> dtoList = new ArrayList<>(proxyList.size());

        for (ProxyEntity proxy : proxyList) {
            String countryName = countryDAO.getNameById(proxy.getCountryId());
            String regionName = regionDAO.getNameById(proxy.getRegionId());
            String cityName = cityDAO.getNameById(proxy.getCityId());
            dtoList.add(ProxyDTO.fromProxyEntity(proxy, countryName, regionName, cityName));
        }

        return dtoList;
    }

    public PageInfoDTO queryPageInfo(
            Integer port, Integer type,
            String country,
            Integer maxConnTime, Integer maxRespTime, Integer minScore) throws EntityNotFoundException {

        Integer countryId = null;
        if (country != null) {
            countryId = countryDAO.getIdByName(country);
        }

        Integer count = proxyDAO.queryCount(port, type, countryId, maxConnTime, maxRespTime, minScore);
        Integer size = ProxyDAO.PAGE_SIZE;

        return new PageInfoDTO(count, size);
    }

    public String extract(Integer port, Integer type, String country,
                          Integer maxConnTime, Integer maxRespTime, Integer minScore, Integer order) throws EntityNotFoundException {

        Integer countryId = null;
        if (country != null) {
            countryId = countryDAO.getIdByName(country);
        }

        List<ProxyEntity> list = proxyDAO.queryNoLimit(port, type, countryId, maxConnTime, maxRespTime, minScore, order);
        StringBuilder builder = new StringBuilder();
        for (ProxyEntity proxy : list) {
            builder.append(String.format("%s:%s\r\n", proxy.getIp(), proxy.getPort()));
        }

        return builder.toString();
    }

    public List<Map<String, Object>> listMostCountry() {
        List<Map<String, Object>> list = proxyDAO.listMostCountry();

        for (Map<String, Object> item : list) {
            Long countryId = (Long) item.get("countryId");
            item.put("country", countryDAO.getNameById(countryId.intValue()));
        }

        return list;
    }

    public List<ProxyEntity> listStable() {
        return proxyDAO.listStable();
    }

    public IpDetailsDTO getDetailsByIp(String ip) throws InvalidIpException {
        IpDetailsDTO details = new IpDetailsDTO();
        LocationBO location = locationManager.getLocationByIp(ip);
        ProxyEntity proxy = proxyDAO.getByIp(ip);

        return IpDetailsDTO.from(ip, location, proxy);
    }

    public ProxyTypeCountBO countProxyByType() {
        Integer http = proxyDAO.countHttp();
        Integer https = proxyDAO.countHttps();
        Integer both = proxyDAO.countBoth();

        return new ProxyTypeCountBO(http, https, both);
    }

    public List<Map<String, Object>> countByCountry() {
        List<Map<String, Object>> countryCountList = proxyDAO.countByCountry();
        List<CountryEntity> countryNameList = countryDAO.listAllCountryName();

        Set<String> existSet = new HashSet<>();
        for (Map<String, Object> item : countryCountList) {
            Long countryId = (Long) item.get("countryId");
            String name = countryDAO.getNameById(countryId.intValue());
            item.put("country", name);
            existSet.add(name);
        }

        for (CountryEntity name : countryNameList) {
            if (!existSet.contains(name.getName())) {
                Map<String, Object> map = new HashMap<>();
                map.put("country", name.getName());
                map.put("count", 0L);
                countryCountList.add(map);
            }
        }

        return countryCountList;
    }
}
