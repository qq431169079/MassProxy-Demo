package com.rainng.massproxyscan.config;

/**
 * 精确验证配置
 */
public class AccConfig {
    // 轮询周期
    public static final int INTERVAL = 1000;
    // 最大队列容量
    public static final int MAX_SIZE = 1000;
    // 工作线程数
    public static final int THREAD_NUM = 256;

    // 连接超时时间
    public static final int CONNECT_TIMEOUT = 3000;
    // 读取Response超时时间
    public static final int READ_TIMEOUT = 8000;

    // 测试轮数
    public static final int TEST_ROUND = 1;
    // 验证HTTP的URL
    public static final String HTTP_URL = "http://proxy-test.rainng.com/";
    // 验证HTTP的正确内容
    public static final String HTTP_EQUALS = "ok";
    // 验证HTTPS的URL
    public static final String HTTPS_URL = "https://proxy-test.rainng.com/";
    // 验证HTTPS的正确内容
    public static final String HTTPS_EQUALS = "ok";
}
