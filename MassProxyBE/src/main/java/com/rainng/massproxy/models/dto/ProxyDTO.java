package com.rainng.massproxy.models.dto;

import com.rainng.massproxy.models.entity.ProxyEntity;
import lombok.Data;

import java.util.Date;

/**
 * 代理
 */
@Data
public class ProxyDTO {
    private Integer id;
    private String ip;
    private Integer port;
    private Integer type;
    private String country;
    private String location;
    private Integer connectTime;
    private Integer responseTime;
    private Integer score;
    private String lastCheckTime;
    private String recordTime;

    public static ProxyDTO fromProxyEntity(ProxyEntity entity, String country, String region, String city) {
        ProxyDTO dto = new ProxyDTO();
        dto.id = entity.getId();
        dto.ip = entity.getIp();
        dto.port = entity.getPort();
        dto.type = entity.getType();
        dto.country = country;
        dto.location = region.equals(city) ? region : region + " " + city;
        dto.connectTime = entity.getConnectTime();
        dto.responseTime = entity.getResponseTime();
        dto.score = entity.getScore();
        dto.lastCheckTime = getTimeString(entity.getLastCheckTime());
        dto.recordTime = getTimeString(entity.getRecordTime());

        return dto;
    }

    private static String getTimeString(Date date) {
        final int SECOND = 1000;
        final int MINUTE = SECOND * 60;
        final int HOUR = MINUTE * 60;
        final int DAY = HOUR * 24;

        Date now = new Date();
        int interval = (int) (now.getTime() - date.getTime());

        int day = interval / DAY;
        interval -= day * DAY;

        int hour = interval / HOUR;
        interval -= hour * HOUR;

        int minute = interval / MINUTE;
        interval -= minute * MINUTE;

        int second = interval / SECOND;

        if (day != 0) {
            return day + "天";
        } else if (hour != 0) {
            return hour + "时";
        } else if (minute != 0) {
            return minute + "分";
        } else if (second != 0) {
            return second + "秒";
        }

        return "0秒";
    }
}
