package com.rainng.massproxy.dao.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Transaction;

import java.util.List;

@Repository
public class BaseRedisDAO {
    @Autowired
    protected JedisPool jedisPool;

    /**
     * 获取键值对
     *
     * @param key 键
     * @return 值
     */
    protected String get(String key) {
        try (Jedis jedis = getJedis()) {
            return jedis.get(key);
        }
    }

    /**
     * 设置键值对
     *
     * @param key   键
     * @param value 值
     */
    protected void set(String key, String value) {
        try (Jedis jedis = getJedis()) {
            jedis.set(key, value);
        }
    }

    /**
     * 增长值
     *
     * @param key 键
     * @return 增长后的值
     */
    protected Long increase(String key) {
        try (Jedis jedis = getJedis()) {
            return jedis.incr(key);
        }
    }

    protected Long increase(String key, long increment) {
        try (Jedis jedis = getJedis()) {
            return jedis.incrBy(key, increment);
        }
    }

    /**
     * 向队列中添加对象
     *
     * @param name  队列名
     * @param items 对象集
     */
    protected void pushList(String name, String[] items) {
        try (Jedis jedis = getJedis()) {
            try (Pipeline pipeline = jedis.pipelined()) {
                for (String item : items) {
                    pipeline.rpush(name, item);
                }
                pipeline.sync();
            }
        }
    }

    /**
     * 从队列中获取对象集
     *
     * @param name     队列名
     * @param maxCount 最大长度
     * @return 对象集
     */
    protected String[] popList(String name, int maxCount) {
        List<Object> list;

        try (Jedis jedis = getJedis()) {
            try (Transaction tran = jedis.multi()) {
                for (int i = 0; i < maxCount; i++) {
                    tran.lpop(name);
                }

                list = tran.exec();
            }
        }

        String[] arr = new String[list.size()];
        list.toArray(arr);

        return arr;
    }

    protected String lfirst(String name) {
        try(Jedis jedis = getJedis()) {
            return jedis.lindex(name, 0);
        }
    }

    protected Jedis getJedis() {
        return jedisPool.getResource();
    }
}
