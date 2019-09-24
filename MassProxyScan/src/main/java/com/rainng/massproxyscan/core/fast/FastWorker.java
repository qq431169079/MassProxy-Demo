package com.rainng.massproxyscan.core.fast;

import com.rainng.massproxyscan.config.FastConfig;
import com.rainng.massproxyscan.config.QueueConfig;
import com.rainng.massproxyscan.core.IWorker;
import com.rainng.massproxyscan.dao.redis.QueueDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 快速验证工作器
 */
@Component
public class FastWorker implements IWorker {
    @Autowired
    private QueueDAO queueDAO;

    @Autowired
    private FastPipeline fastPipeline;

    @Override
    public void run() {
        fastPipeline.start();
        while (true) {
            try {
                checkWaitQueue();
                checkOkQueue();
                Thread.sleep(FastConfig.INTERVAL);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.format("%s %s %s %s\r\n",
                    fastPipeline.getWaitQueue().size(), fastPipeline.getHttpQueue().size(),
                    fastPipeline.getHttpsQueue().size(), fastPipeline.getOkQueue().size());
        }
    }

    private void checkWaitQueue() {
        if (queueDAO.getOpenLength() <= 0) {
            return;
        }

        if (fastPipeline.getWaitQueue().size() >= FastConfig.MAX_SIZE) {
            return;
        }

        String[] proxies = queueDAO.getOpenList();
        fastPipeline.add(proxies);
    }

    private void checkOkQueue() {
        int queueSize = fastPipeline.getOkQueue().size();
        if (queueSize <= 0) {
            return;
        }

        int pushSize = Math.min(queueSize, QueueConfig.OK_PER_PUSH);
        String[] proxies = new String[pushSize];
        for (int i = 0; i < proxies.length; i++) {
            proxies[i] = fastPipeline.getOkQueue().poll();
        }

        queueDAO.pushOkList(proxies);
        queueDAO.increaseOpenCount(proxies.length);
    }
}
