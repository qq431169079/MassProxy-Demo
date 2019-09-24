package com.rainng.massproxy.dao.redis;

import com.rainng.massproxy.config.QueueConfig;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

@Repository
public class QueueDAO extends BaseRedisDAO {
    /**
     * 获取任务队列长度
     *
     * @return 任务队列长度
     */
    public int getTaskLength() {
        try (Jedis jedis = getJedis()) {
            return jedis.llen(QueueConfig.TASK_NAME).intValue();
        }
    }

    /**
     * 从队列获取扫描任务
     *
     * @return 扫描任务
     */
    public String getTask() {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.lpop(QueueConfig.TASK_NAME);
        }
    }

    /**
     * 添加扫描任务到队列
     *
     * @param items 任务集
     */
    public void pushTaskList(String[] items) {
        pushList(QueueConfig.TASK_NAME, items);
    }

    /**
     * 获取开放端口队列长度
     *
     * @return 开放端口队列长度
     */
    public int getOpenLength() {
        try (Jedis jedis = getJedis()) {
            return jedis.llen(QueueConfig.OPEN_NAME).intValue();
        }
    }

    /**
     * 从队列获取开放端口
     *
     * @return 开放端口
     */
    public String[] getOpenList() {
        return popList(QueueConfig.OPEN_NAME, QueueConfig.OPEN_PER_PEAK);
    }

    /**
     * 向队列添加开放端口
     *
     * @param items 开放端口集
     */
    public void pushOpenList(String[] items) {
        pushList(QueueConfig.OPEN_NAME, items);
    }

    /**
     * 获取基本可用队列长度
     *
     * @return 基本可用队列长度
     */
    public int getOkLength() {
        try (Jedis jedis = getJedis()) {
            return jedis.llen(QueueConfig.OK_NAME).intValue();
        }
    }

    /**
     * 从队列中获取基本可用地址
     *
     * @return 基本可用地址
     */
    public String[] getOkList() {
        return popList(QueueConfig.OK_NAME, QueueConfig.OK_PER_PEAK);
    }

    /**
     * 向队列中添加基本可用地址
     *
     * @param items 基本可用地址集
     */
    public void pushOkList(String[] items) {
        pushList(QueueConfig.OK_NAME, items);
    }

    /**
     * 获取爬虫任务队列长度
     *
     * @return 爬虫任务队列长度
     */
    public int getCrawlLength() {
        try (Jedis jedis = getJedis()) {
            return jedis.llen(QueueConfig.CRAWL_NAME).intValue();
        }
    }

    /**
     * 从队列中获取爬虫任务
     *
     * @return 爬虫任务
     */
    public String[] getCrawlList() {
        return popList(QueueConfig.CRAWL_NAME, QueueConfig.CRAWL_PER_PEAK);
    }

    /**
     * 向队列中添加爬虫任务
     *
     * @param items 爬虫任务集
     */
    public void pushCrawlList(String[] items) {
        pushList(QueueConfig.CRAWL_NAME, items);
    }

    public void increaseTaskCount(Integer count) {
        increase(QueueConfig.TASK_COUNT, count);
    }

    public void increaseOpenCount(Integer count) {
        increase(QueueConfig.OPEN_COUNT, count);
    }

    public void increaseOkCount(Integer count) {
        increase(QueueConfig.OK_COUNT, count);
    }

    public void increaseCrawkCount(Integer count) {
        increase(QueueConfig.CRAWL_COUNT, count);
    }

    public void resetTaskCount() {
        set(QueueConfig.TASK_COUNT, "0");
    }

    public void resetOpenCount() {
        set(QueueConfig.OPEN_COUNT, "0");
    }

    public void resetOkCount() {
        set(QueueConfig.OK_COUNT, "0");
    }

    public void resetCrawlCount() {
        set(QueueConfig.CRAWL_COUNT, "0");
    }

    public Long getTaskCount() {
        String value = get(QueueConfig.TASK_COUNT);
        if(value == null || value.equals("")) {
            value = "0";
        }
        return Long.valueOf(value);
    }

    public Long getOpenCount() {
        String value = get(QueueConfig.OPEN_COUNT);
        if(value == null || value.equals("")) {
            value = "0";
        }
        return Long.valueOf(value);
    }

    public Long getOkCount() {
        String value = get(QueueConfig.OK_COUNT);
        if(value == null || value.equals("")) {
            value = "0";
        }
        return Long.valueOf(value);
    }

    public Long getCrawlCount() {
        String value = get(QueueConfig.CRAWL_COUNT);
        if(value == null || value.equals("")) {
            value = "0";
        }
        return Long.valueOf(value);
    }

    public String lookTask() {
        return lfirst(QueueConfig.TASK_NAME);
    }

    public String lookFast() {
        return lfirst(QueueConfig.OPEN_NAME);
    }

    public String lookAcc() {
        return lfirst(QueueConfig.OK_NAME);
    }

    public String lookCrawl() {
        return lfirst(QueueConfig.CRAWL_NAME);
    }
}
