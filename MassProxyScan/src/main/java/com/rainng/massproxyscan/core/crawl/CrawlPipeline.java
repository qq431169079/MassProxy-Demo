package com.rainng.massproxyscan.core.crawl;

import com.rainng.massproxyscan.config.CrawlConfig;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 爬虫管道
 */
@Component
public class CrawlPipeline {
    @Getter
    private final Queue<String> urlQueue = new ConcurrentLinkedQueue<>();
    @Getter
    private final Queue<String> okQueue = new ConcurrentLinkedQueue<>();

    private volatile boolean running = false;

    private static void threadSleep() {
        try {
            Thread.sleep(CrawlConfig.INTERVAL);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 向队列中添加待爬取Url集
     *
     * @param urls Url集
     */
    public void add(String[] urls) {
        for (String url : urls) {
            if (url == null) {
                break;
            }
            urlQueue.add(url);
        }
    }

    /**
     * 开始爬取
     */
    public synchronized void start() {
        if (running) {
            return;
        }

        running = true;

        for (int i = 0; i < CrawlConfig.THREAD_NUM; i++) {
            new Thread(this::crawl).start();
        }
    }

    private void crawl() {
        GenericCrawler crawler = new GenericCrawler();
        while (running) {
            String url = urlQueue.poll();
            if (url == null) {
                threadSleep();
                continue;
            }

            List<String> proxyList = crawler.crawl(url);
            okQueue.addAll(proxyList);
        }
    }
}
