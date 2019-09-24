package com.rainng.massproxy.service;

import com.rainng.massproxy.manager.LocationManager;
import com.rainng.massproxy.models.dto.ResultDTO;
import com.rainng.massproxy.models.exception.EntityNotFoundException;
import com.rainng.massproxy.models.exception.InvalidIpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationService extends BaseService {
    @Autowired
    private LocationManager manager;

    public ResultDTO listAvailableCountry() {
        return result(manager.listAvailableCountry());
    }

    public ResultDTO listAvailableRegion(String country) {
        try {
            return result(manager.listAvailableRegion(country));
        } catch (EntityNotFoundException ex) {
            return result(new String[0]);
        }
    }

    public ResultDTO getIpDetails(String ip) {
        try {
            return result(manager.getIpDetails(ip));
        } catch (InvalidIpException ex) {
            return failedResult(ex.getMessage());
        }

    }
}
