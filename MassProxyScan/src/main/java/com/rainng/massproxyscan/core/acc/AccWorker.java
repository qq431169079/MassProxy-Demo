package com.rainng.massproxyscan.core.acc;

import com.rainng.massproxyscan.config.AccConfig;
import com.rainng.massproxyscan.core.IWorker;
import com.rainng.massproxyscan.dao.ProxyDAO;
import com.rainng.massproxyscan.dao.redis.QueueDAO;
import com.rainng.massproxyscan.manager.LocationManager;
import com.rainng.massproxyscan.models.bo.LocationIdBO;
import com.rainng.massproxyscan.models.entity.ProxyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 精确验证工作器
 */
@Component
public class AccWorker implements IWorker {
    @Autowired
    private QueueDAO queueDAO;
    @Autowired
    private ProxyDAO proxyDAO;
    @Autowired
    private LocationManager locationManager;

    @Autowired
    private AccPipeline accPipeline;

    @Override
    public void run() {
        accPipeline.start();
        while (true) {
            try {
                checkOkQueue();
                checkValidQueue();
                Thread.sleep(AccConfig.INTERVAL);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.format("%s %s\r\n", accPipeline.getOkQueue().size(), accPipeline.getValidQueue().size());
        }
    }

    private void checkOkQueue() {
        if (queueDAO.getOkLength() <= 0) {
            return;
        }

        if (accPipeline.getOkQueue().size() >= AccConfig.MAX_SIZE) {
            return;
        }

        String[] proxies = queueDAO.getOkList();
        accPipeline.add(proxies);
    }

    private void checkValidQueue() {
        int queueSize = accPipeline.getValidQueue().size();
        if (queueSize <= 0) {
            return;
        }

        queueDAO.increaseOkCount(queueSize);
        for (int i = 0; i < queueSize; i++) {
            AccTestResult result = accPipeline.getValidQueue().poll();
            if (result == null) {
                break;
            }

            proxyDAO.insert(createProxyEntityFromResult(result));
        }
    }

    private ProxyEntity createProxyEntityFromResult(AccTestResult result) {
        ProxyEntity entity = new ProxyEntity();
        entity.setIp(result.getIp());
        entity.setPort(result.getPort());
        entity.setType(result.getType());
        entity.setAnonymity(true);

        LocationIdBO location = locationManager.getLocationIdByIp(result.getIp());
        entity.setCountryId(location.getCountryId());
        entity.setRegionId(location.getRegionId());
        entity.setCityId(location.getCityId());
        entity.setConnectTime(result.getConnectTime());
        entity.setResponseTime(result.getResponseTime());

        return entity;
    }
}
