package com.rainng.massproxyscan.core.verify;

import com.rainng.massproxyscan.config.AccConfig;
import com.rainng.massproxyscan.config.VerifyConfig;
import com.rainng.massproxyscan.core.acc.AccProxyTester;
import com.rainng.massproxyscan.core.acc.AccTestResult;
import com.rainng.massproxyscan.models.constant.ProxyType;
import com.rainng.massproxyscan.models.entity.ProxyEntity;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 长期验证管道
 */
@Component
public class VerifyPipeline {
    @Getter
    private final Queue<ProxyEntity> waitQueue = new ConcurrentLinkedQueue<>();
    @Getter
    private final Queue<ProxyEntity> okQueue = new ConcurrentLinkedQueue<>();

    private volatile boolean running = false;

    private static void threadSleep() {
        try {
            Thread.sleep(AccConfig.INTERVAL);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 向队列中添加待验证代理集
     *
     * @param proxyList 代理集
     */
    public void add(List<ProxyEntity> proxyList) {
        waitQueue.addAll(proxyList);
    }

    /**
     * 开始检测
     */
    public synchronized void start() {
        if (running) {
            return;
        }

        running = true;

        for (int i = 0; i < VerifyConfig.THREAD_NUM; i++) {
            new Thread(this::verify).start();
        }
    }

    private void verify() {
        while (running) {
            ProxyEntity proxy = waitQueue.poll();
            if (proxy == null) {
                threadSleep();
                continue;
            }

            String address = proxy.getIp() + ":" + proxy.getPort();
            AccProxyTester tester = new AccProxyTester();
            AccTestResult result = tester.testAll(address, VerifyConfig.TEST_ROUND);

            proxy.setLastCheckTime(new Date());
            okQueue.add(proxy);

            if (result.getType() == ProxyType.BAD) {
                proxy.setScore(Math.max(proxy.getScore() - VerifyConfig.MINUS_POINTS, 0));
                continue;
            } else {
                proxy.setScore(proxy.getScore() + VerifyConfig.BONUS_POINTS);
            }

            proxy.setType(result.getType());
            proxy.setConnectTime(result.getConnectTime());
            proxy.setResponseTime(result.getResponseTime());
        }
    }
}
