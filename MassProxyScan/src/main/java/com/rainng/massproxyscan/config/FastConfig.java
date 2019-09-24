package com.rainng.massproxyscan.config;

/**
 * 快速验证配置
 */
public class FastConfig {
    // 轮询周期
    public static final int INTERVAL = 1000;
    // 最大队列容量
    public static final int MAX_SIZE = 3000;

    // 开放端口队列工作线程数 4:8:1
    public static final int THREAD_OPEN_NUM = 64 * 4;
    // HTTP验证队列工作线程数
    public static final int THREAD_HTTP_NUM = 128 * 4;
    // HTTPS验证队列工作线程数
    public static final int THREAD_HTTPS_NUM = 24 * 4;

    // 响应等待时长
    public static final int WAIT_TIME = 5000;
    // 繁忙时等待的时间
    public static final int BUSY_TIME = 200;
    // 连接超时时间
    public static final int CONNECT_TIMEOUT = 1500;
    // 请求/响应超时时间
    public static final int SEND_READ_TIMEOUT = 3000;

    // HTTP请求地址
    public static final String HTTP_REQUEST = "GET http://www.baidu.com/ HTTP/1.1\r\nHost: www.baidu.com\r\n\r\n";
    // HTTPS请求地址
    public static final String HTTPS_REQUEST = "CONNECT www.baidu.com:443 HTTP/1.1\r\nHost: www.baidu.com:443\r\n\r\n";
}
