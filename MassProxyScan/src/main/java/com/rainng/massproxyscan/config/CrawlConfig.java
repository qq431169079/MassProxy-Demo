package com.rainng.massproxyscan.config;

public class CrawlConfig {
    // 轮询周期
    public static final int INTERVAL = 1000;
    // 最大队列容量
    public static final int MAX_SIZE = 10;

    // 工作线程数, 为了防止反爬 应减少线程数
    public static final int THREAD_NUM = 2;
}
