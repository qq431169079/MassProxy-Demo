package com.rainng.massproxyscan.core.verify;

import com.rainng.massproxyscan.config.VerifyConfig;
import com.rainng.massproxyscan.core.IWorker;
import com.rainng.massproxyscan.manager.VerifyManager;
import com.rainng.massproxyscan.models.entity.ProxyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 长期验证工作器
 */
@Component
public class VerifyWorker implements IWorker {
    @Autowired
    private VerifyManager verifyManager;
    @Autowired
    private VerifyPipeline verifyPipeline;

    @Override
    public void run() {
        verifyPipeline.start();
        while (true) {
            try {
                checkWaitQueue();
                checkOkQueue();
                Thread.sleep(VerifyConfig.INTERVAL);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.format("%s %s\r\n", verifyPipeline.getWaitQueue().size(), verifyPipeline.getOkQueue().size());
        }
    }

    private void checkWaitQueue() {
        if (verifyPipeline.getWaitQueue().size() >= VerifyConfig.MAX_SIZE) {
            return;
        }

        List<ProxyEntity> list = verifyManager.getNextProxyList();
        verifyPipeline.add(list);
    }

    private void checkOkQueue() {
        int queueSize = verifyPipeline.getOkQueue().size();
        if (queueSize <= 0) {
            return;
        }

        for (int i = 0; i < queueSize; i++) {
            ProxyEntity proxy = verifyPipeline.getOkQueue().poll();
            if (proxy == null) {
                break;
            }

            verifyManager.updateProxy(proxy);
        }
    }
}
