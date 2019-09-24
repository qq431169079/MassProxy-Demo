package com.rainng.massproxy.config;

/**
 * 队列配置
 */
public class QueueConfig {
    // 扫描任务队列
    public static final String TASK_NAME = "ph_queue_task";
    // 开放端口队列
    public static final String OPEN_NAME = "ph_queue_open";
    // 检查通过队列
    public static final String OK_NAME = "ph_queue_ok";
    // 爬虫任务队列
    public static final String CRAWL_NAME = "ph_queue_crawl";

    // 扫描结果计数
    public static final String TASK_COUNT = "ph_queue_task_count";
    // 快速验证结果计数
    public static final String OPEN_COUNT = "ph_queue_open_count";
    // 精确验证结果计数
    public static final String OK_COUNT = "ph_queue_ok_count";
    // 爬取结果计数
    public static final String CRAWL_COUNT = "ph_queue_crawl_count";

    // 开放端口队列每次推送的最大数据量
    public static final int OPEN_PER_PUSH = 50000;
    // 开放端口队列每次获取的最大数据量
    public static final int OPEN_PER_PEAK = 5000;
    // 检查通过队列每次推送的数据量
    public static final int OK_PER_PUSH = 100;
    // 检查通过队列每次获取的数据量
    public static final int OK_PER_PEAK = 100;
    // 爬虫任务队列每次推送的数据量
    public static final int CRAWL_PER_PUSH = 50;
    // 爬虫任务队列每次获取的数据量
    public static final int CRAWL_PER_PEAK = 5;

}
