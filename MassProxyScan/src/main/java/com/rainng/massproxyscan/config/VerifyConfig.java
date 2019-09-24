package com.rainng.massproxyscan.config;

/**
 * 验证配置
 */
public class VerifyConfig {
    // 轮询周期
    public static final int INTERVAL = 1000;

    // Redis存放的对应MySQL代理表中索引
    public static final String INDEX_NAME = "ph_verify_index";
    // MySQL代理表中最大Id缓存
    public static final String INDEX_MAX = "ph_verify_max";

    // 测试轮数
    public static final int TEST_ROUND = 1;

    // Index步长
    public static final int STEP_LENGTH = 35;
    // 最大容量
    public static final int MAX_SIZE = 60;
    // 工作线程数
    public static final int THREAD_NUM = 64;

    // 初始分数
    public static final int INIT_POINTS = 20;
    // 不可用时减分
    public static final int MINUS_POINTS = 3;
    // 可用时加分
    public static final int BONUS_POINTS = 1;
}
