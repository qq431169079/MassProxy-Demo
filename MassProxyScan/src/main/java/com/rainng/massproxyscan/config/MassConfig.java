package com.rainng.massproxyscan.config;

/**
 * 大量扫描配置
 */
public class MassConfig {
    // 轮询周期
    public static final int INTERVAL = 1000;

    // Masscan运行参数
    public static final String MASSCAN_ARGS = "masscan --exclude 255.255.255.255 --seed 1296 -oL addresslist.txt  --rate 50000 ";
    // Masscan恢复参数
    public static final String MASSCAN_RESUME_ARGS = "masscan --exclude 255.255.255.255 --resume paused.conf ";
    // Masscan暂停记录文件名
    public static final String PAUSE_FILE_NAME = "paused.conf";
    // Masscan扫描结果文件名
    public static final String RESULT_FILE_NAME = "addresslist.txt";

    // 任务示例
    private static final String TASK_DEMO = "0.0.0.0/0 -p8080,3128 --shard 1/10000";
}
