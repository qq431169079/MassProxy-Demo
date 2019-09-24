package com.rainng.massproxyscan.core.acc;

import lombok.Data;

/**
 * 精确验证结果
 */
@Data
public class AccTestResult {
    /**
     * Ip地址
     */
    private String ip;
    /**
     * 端口
     */
    private int port;
    /**
     * 类型(ProxyType)
     */
    private int type = 0;
    /**
     * 连接时间
     */
    private int connectTime = Integer.MAX_VALUE;
    /**
     * 响应时间
     */
    private int responseTime = Integer.MAX_VALUE;

    public AccTestResult() {
    }

    /**
     * 设置连接时间(如果更小)
     *
     * @param time 连接时间
     */
    public void setMinimalConnectTime(long time) {
        if (time < connectTime) {
            connectTime = (int) time;
        }
    }

    /**
     * 设置响应时间(如果更小)
     *
     * @param time 响应时间
     */
    public void setMinimalResponseTime(long time) {
        if (time < responseTime) {
            responseTime = (int) time;
        }
    }

    public void httpAvailable() {
        type += 1;
    }

    public void httpsAvailable() {
        type += 2;
    }
}
