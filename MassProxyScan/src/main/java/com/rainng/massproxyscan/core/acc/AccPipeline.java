package com.rainng.massproxyscan.core.acc;

import com.rainng.massproxyscan.config.AccConfig;
import com.rainng.massproxyscan.models.constant.ProxyType;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 精确验证管道
 */
@Component
public class AccPipeline {
    @Getter
    private final Queue<String> okQueue = new ConcurrentLinkedQueue<>();
    @Getter
    private final Queue<AccTestResult> validQueue = new ConcurrentLinkedQueue<>();

    private volatile boolean running = false;

    private static void threadSleep() {
        try {
            Thread.sleep(AccConfig.INTERVAL);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 向队列中添加待检测代理集
     *
     * @param proxies 代理集
     */
    public void add(String[] proxies) {
        for (String proxy : proxies) {
            if (proxy == null) {
                break;
            }
            okQueue.add(proxy);
        }
    }

    /**
     * 开始检测
     */
    public synchronized void start() {
        if (running) {
            return;
        }

        running = true;

        for (int i = 0; i < AccConfig.THREAD_NUM; i++) {
            new Thread(this::verify).start();
        }
    }

    private void verify() {
        while (running) {
            String proxy = okQueue.poll();
            if (proxy == null) {
                threadSleep();
                continue;
            }

            AccProxyTester tester = new AccProxyTester();
            AccTestResult result = tester.testAll(proxy, AccConfig.TEST_ROUND);

            if (result.getType() != ProxyType.BAD) {
                validQueue.add(result);
            }
        }
    }
}
