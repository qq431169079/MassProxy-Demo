package com.rainng.massproxy.service;

import com.rainng.massproxy.manager.ProxyManager;
import com.rainng.massproxy.models.dto.ProxyTypeCountBO;
import com.rainng.massproxy.models.dto.ResultDTO;
import com.rainng.massproxy.models.entity.ProxyEntity;
import com.rainng.massproxy.models.exception.EntityNotFoundException;
import com.rainng.massproxy.models.exception.InvalidIpException;
import com.rainng.massproxy.util.NameValue;
import com.rainng.massproxy.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ProxyService extends BaseService {
    @Autowired
    private ProxyManager manager;

    public ResultDTO query(Integer page,
                           Integer port, Integer type, String country,
                           Integer maxConnTime, Integer maxRespTime, Integer minScore, Integer order) {
        try {
            return result(manager.query(page, port, type, country, maxConnTime, maxRespTime, minScore, order));
        } catch (EntityNotFoundException ex) {
            return failedResult(ex.getMessage());
        }
    }

    public ResultDTO queryPageInfo(
            Integer port, Integer type,
            String country,
            Integer maxConnTime, Integer maxRespTime, Integer minScore) {
        try {
            return result(manager.queryPageInfo(port, type, country, maxConnTime, maxRespTime, minScore));
        } catch (EntityNotFoundException ex) {
            return failedResult(ex.getMessage());
        }
    }

    public ResultDTO extract(Integer port, Integer type, String country,
                             Integer maxConnTime, Integer maxRespTime, Integer minScore, Integer order) {
        try {
            return result(manager.extract(port, type, country, maxConnTime, maxRespTime, minScore, order));
        } catch (EntityNotFoundException ex) {
            return failedResult(ex.getMessage());
        }
    }

    public ResultDTO countByType() {
        List<NameValue<String, Integer>> list = new ArrayList<>();

        ProxyTypeCountBO countBO = manager.countProxyByType();
        list.add(new NameValue<>("HTTP", countBO.getHttp()));
        list.add(new NameValue<>("HTTPS", countBO.getHttps()));
        list.add(new NameValue<>("通用", countBO.getBoth()));

        return result(list);
    }

    public ResultDTO countByCountry() {
        List<NameValue<String, Integer>> nameValueList = new ArrayList<>();
        List<Map<String, Object>> mapList = manager.countByCountry();

        for (Map<String, Object> item : mapList) {
            if (item.get("country").equals("-")) {
                continue;
            }
            String county = (String) item.get("country");
            Long count = (Long) item.get("count");
            nameValueList.add(new NameValue<String, Integer>(county, count.intValue()));
        }

        return result(nameValueList);
    }

    public ResultDTO listMostCountry() {
        return result(manager.listMostCountry());
    }


    public ResultDTO getDetails(String ip) {
        try {
            return result(manager.getDetailsByIp(ip));
        } catch (InvalidIpException ex) {
            return failedResult(ex.getMessage());
        }
    }

    public ResultDTO listStable() {
        List<Pair<String, Integer>> stableList = new ArrayList<>();
        List<ProxyEntity> proxyList = manager.listStable();

        for (ProxyEntity proxy : proxyList) {
            stableList.add(new Pair<>(proxy.getIp(), proxy.getPort()));
        }

        return result(stableList);
    }

    public ResultDTO tcpPing(String ip, Integer port) {
        long time = innerTcpPing(ip, port);
        if (time == -1) {
            return failedResult("Ping失败, 无法连接目标");
        }

        // 测试两次取最小值
        long time2 = innerTcpPing(ip, port);
        if (time2 == -1) {
            return result(time);
        }

        return result(Math.min(time, time2));
    }

    private long innerTcpPing(String ip, Integer port) {
        try (Socket socket = new Socket()) {
            long startTime = System.currentTimeMillis();
            socket.connect(new InetSocketAddress(ip, port), 5000);
            return System.currentTimeMillis() - startTime;
        } catch (IOException ex) {
            return -1;
        }
    }
}
