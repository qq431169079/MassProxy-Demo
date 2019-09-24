package com.rainng.massproxyscan.core.crawl;

import com.rainng.massproxyscan.config.CrawlConfig;
import com.rainng.massproxyscan.config.QueueConfig;
import com.rainng.massproxyscan.core.IWorker;
import com.rainng.massproxyscan.dao.redis.QueueDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CrawlWorker implements IWorker {
    @Autowired
    private QueueDAO queueDAO;
    @Autowired
    private CrawlPipeline crawlPipeline;

    @Override
    public void run() {
        crawlPipeline.start();
        while (true) {
            try {
                checkCrawlQueue();
                checkOkQueue();
                Thread.sleep(CrawlConfig.INTERVAL);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.format("%s %s\r\n",
                    crawlPipeline.getUrlQueue().size(), crawlPipeline.getOkQueue().size());
        }
    }

    private void checkCrawlQueue() {
        if (queueDAO.getCrawlLength() <= 0) {
            return;
        }

        if (crawlPipeline.getUrlQueue().size() >= CrawlConfig.MAX_SIZE) {
            return;
        }

        String[] urls = queueDAO.getCrawlList();
        crawlPipeline.add(urls);
    }

    private void checkOkQueue() {
        int queueSize = crawlPipeline.getOkQueue().size();
        if (queueSize <= 0) {
            return;
        }

        String[] proxies = new String[Math.min(queueSize, QueueConfig.OK_PER_PUSH)];
        for (int i = 0; i < proxies.length; i++) {
            proxies[i] = crawlPipeline.getOkQueue().poll();
        }

        queueDAO.pushOkList(proxies);
        queueDAO.increaseCrawlCount(proxies.length);

        if (proxies.length == QueueConfig.OK_PER_PUSH) {
            checkOkQueue();
        }
    }
}
