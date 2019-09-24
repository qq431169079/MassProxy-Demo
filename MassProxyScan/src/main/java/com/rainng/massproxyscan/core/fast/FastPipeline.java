package com.rainng.massproxyscan.core.fast;

import com.rainng.massproxyscan.config.FastConfig;
import com.rainng.massproxyscan.models.constant.FastTestStatus;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 快速验证管道
 */
@Component
public class FastPipeline {
    @Getter
    private final Queue<String> waitQueue = new ConcurrentLinkedQueue<>();
    @Getter
    private final Queue<FastProxyTester> httpQueue = new ConcurrentLinkedQueue<>();
    @Getter
    private final Queue<FastProxyTester> httpsQueue = new ConcurrentLinkedQueue<>();
    @Getter
    private final Queue<String> okQueue = new ConcurrentLinkedQueue<>();

    private volatile boolean running = false;

    private static void threadSleep(int time) {
        try {
            Thread.sleep(time);
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
            waitQueue.add(proxy);
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

        for (int i = 0; i < FastConfig.THREAD_OPEN_NUM; i++) {
            new Thread(this::checkConnect).start();
        }

        for (int i = 0; i < FastConfig.THREAD_HTTP_NUM; i++) {
            new Thread(this::checkHttp).start();
        }

        for (int i = 0; i < FastConfig.THREAD_HTTPS_NUM; i++) {
            new Thread(this::checkHttps).start();
        }
    }

    /**
     * 测试连接
     */
    private void checkConnect() {
        while (running) {
            if (httpQueue.size() > FastConfig.THREAD_HTTP_NUM * 4) {
                threadSleep(FastConfig.BUSY_TIME);
                continue;
            }

            String proxy = waitQueue.poll();
            if (proxy == null) {
                threadSleep(FastConfig.INTERVAL);
                continue;
            }

            FastProxyTester tester = new FastProxyTester(proxy);
            if (tester.startTestHttp() == FastTestStatus.NEXT) {
                httpQueue.add(tester);
            }
        }
    }

    /**
     * 测试Http代理
     */
    private void checkHttp() {
        while (running) {
            if (httpQueue.size() == 0) {
                threadSleep(FastConfig.BUSY_TIME);
                continue;
            }

            FastProxyTester tester = httpQueue.poll();
            if (tester == null) {
                continue;
            }
            tester.sleep();

            int status = tester.endTestHttp();
            if (status == FastTestStatus.OK) {
                okQueue.add(tester.getAddress());
            } else if (status == FastTestStatus.NEXT) {
                if (tester.startTestHttps() == FastTestStatus.NEXT) {
                    httpsQueue.add(tester);
                }
            }
        }
    }

    /**
     * 测试Https代理
     */
    private void checkHttps() {
        while (running) {
            if (httpsQueue.size() == 0) {
                threadSleep(FastConfig.BUSY_TIME);
                continue;
            }

            FastProxyTester tester = httpsQueue.poll();
            if (tester == null) {
                continue;
            }
            tester.sleep();

            int status = tester.endTestHttps();
            if (status == FastTestStatus.OK) {
                okQueue.add(tester.getAddress());
            }
        }
    }
}
