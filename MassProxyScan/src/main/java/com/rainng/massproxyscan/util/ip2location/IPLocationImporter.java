package com.rainng.massproxyscan.util.ip2location;

import com.rainng.massproxyscan.dao.CityDAO;
import com.rainng.massproxyscan.dao.CountryDAO;
import com.rainng.massproxyscan.dao.RangeDAO;
import com.rainng.massproxyscan.dao.RegionDAO;
import com.rainng.massproxyscan.models.entity.CityEntity;
import com.rainng.massproxyscan.models.entity.CountryEntity;
import com.rainng.massproxyscan.models.entity.RangeEntity;
import com.rainng.massproxyscan.models.entity.RegionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class IPLocationImporter {
    @Autowired
    private CountryDAO countryDAO;
    @Autowired
    private RegionDAO regionDAO;
    @Autowired
    private CityDAO cityDAO;
    @Autowired
    private RangeDAO rangeDAO;


    public void importIPLocationToDb(String path) throws IOException {
        List<IP2LocationRecord> list = IP2LocationRecord.parseFromFile(path);
        Map<String, Integer> cityMap = new HashMap<>();

        insertCountryRegionCity(list, cityMap);
        insertRange(list, cityMap);
    }


    private void insertCountryRegionCity(List<IP2LocationRecord> list, Map<String, Integer> cityMap) {
        Map<String, Integer> countryMap = new HashMap<>();
        Map<String, Integer> regionMap = new HashMap<>();

        for (IP2LocationRecord record : list) {
            String country = record.getCountry();
            String region = String.format("%s|%s", record.getCountry(), record.getRegion());
            String city = String.format("%s|%s|%s", record.getCountry(), record.getRegion(), record.getCity());

            if (!countryMap.containsKey(country)) {
                insertCountry(record.getCountry(), record.getCountryCode());
                countryMap.put(country, countryMap.size() + 1);
            }

            if (!regionMap.containsKey(region)) {
                insertRegion(record.getRegion(), countryMap.get(country));
                regionMap.put(region, regionMap.size() + 1);
            }

            if (!cityMap.containsKey(city)) {
                insertCity(record.getCity(), regionMap.get(region));
                cityMap.put(city, cityMap.size() + 1);
            }
        }

        System.out.println(countryMap.size());
        System.out.println(regionMap.size());
        System.out.println(cityMap.size());
    }

    private void insertRange(List<IP2LocationRecord> list, Map<String, Integer> cityMap) {
        System.out.println(list.size());
        for (IP2LocationRecord record : list) {
            String city = String.format("%s|%s|%s", record.getCountry(), record.getRegion(), record.getCity());
            RangeEntity range = new RangeEntity(record.getFrom(), record.getTo(), cityMap.get(city), record.getLatitude(), record.getLongitude());
            rangeDAO.insert(range);
        }
    }


    private void insertCountry(String country, String countryCode) {
        countryDAO.insert(new CountryEntity(countryCode, country));
    }

    private void insertRegion(String region, Integer countryId) {
        regionDAO.insert(new RegionEntity(countryId, region));
    }

    private void insertCity(String city, Integer regionId) {
        cityDAO.insert(new CityEntity(regionId, city));
    }
}
