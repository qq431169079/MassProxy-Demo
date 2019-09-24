package com.rainng.massproxy.models.dto;

import com.rainng.massproxy.models.bo.LocationBO;
import com.rainng.massproxy.models.entity.ProxyEntity;
import lombok.Data;

import java.util.Date;

/**
 * IP详细信息
 */
@Data
public class IpDetailsDTO {
    private String ip;
    private String countryCode;
    private String country;
    private String region;
    private String city;
    private Double latitude;
    private Double longitude;
    private Boolean inProxyDatabase;
    private Integer port;
    private Integer type;
    private Integer connectTime;
    private Integer responseTime;
    private Integer score;
    private Date lastCheckTime;
    private Date recordTime;

    public static IpDetailsDTO from(String ip, LocationBO location, ProxyEntity proxy) {
        IpDetailsDTO details = new IpDetailsDTO();
        details.ip = ip;
        details.countryCode = location.getCountryCode();
        details.country = location.getCountry();
        details.region = location.getRegion();
        details.city = location.getCity();
        details.latitude = location.getLatitude();
        details.longitude = location.getLongitude();
        details.inProxyDatabase = proxy != null;
        if (!details.inProxyDatabase) {
            return details;
        }

        details.port = proxy.getPort();
        details.type = proxy.getType();
        details.connectTime = proxy.getConnectTime();
        details.responseTime = proxy.getResponseTime();
        details.score = proxy.getScore();
        details.lastCheckTime = proxy.getLastCheckTime();
        details.recordTime = proxy.getRecordTime();

        return details;
    }
}
