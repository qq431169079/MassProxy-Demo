package com.rainng.massproxyscan.core.fast;

import com.rainng.massproxyscan.config.FastConfig;
import com.rainng.massproxyscan.models.constant.FastTestStatus;
import com.rainng.massproxyscan.util.SimpleSocket;
import lombok.Getter;
import lombok.Setter;

/**
 * 快速代理验证器
 */
public class FastProxyTester {
    private final SimpleSocket socket = new SimpleSocket();

    @Getter
    @Setter
    private int waitTime = FastConfig.WAIT_TIME;

    @Getter
    private String address;

    public FastProxyTester(String address) {
        this.address = address;
    }

    /**
     * 开始测试Http代理
     *
     * @return 测试状态
     */
    public int startTestHttp() {
        if (socket.connect(address)) {
            if (socket.send(FastConfig.HTTP_REQUEST)) {
                return FastTestStatus.NEXT;
            }
        }

        disConnect();
        return FastTestStatus.BAD;
    }

    /**
     * 结束验证Http代理
     *
     * @return 测试状态
     */
    public int endTestHttp() {
        String data = socket.receive();
        if (data.equals("")) {
            disConnect();
            return FastTestStatus.BAD;
        }

        if (data.startsWith("HTTP")) {
            if (data.contains("200")) {
                disConnect();
                return FastTestStatus.OK;
            }
            if (data.contains("400") || data.contains("403")) {
                return FastTestStatus.NEXT;
            }
        }

        disConnect();
        return FastTestStatus.BAD;
    }

    /**
     * 开始测试Https代理
     *
     * @return 测试状态
     */
    public int startTestHttps() {
        if (socket.connect(address)) {
            if (socket.send(FastConfig.HTTPS_REQUEST)) {
                return FastTestStatus.NEXT;
            }
        }

        disConnect();
        return FastTestStatus.BAD;
    }

    /**
     * 结束验证Https代理
     *
     * @return 测试状态
     */
    public int endTestHttps() {
        String data = socket.receive();
        disConnect();
        if (data.startsWith("HTTP") && data.contains("200")) {
            return FastTestStatus.OK;
        }

        return FastTestStatus.BAD;
    }

    /**
     * 休眠线程到等待时间
     */
    public void sleep() {
        try {
            Thread.sleep(getSleepTime());
        } catch (InterruptedException ex) {

        }
    }

    /**
     * 获取需要休眠的时间
     *
     * @return 需要休眠的时间
     */
    private int getSleepTime() {
        int pastTime = (int) (System.currentTimeMillis() - socket.getLastSendTime());
        return Math.max(waitTime - pastTime, 0);
    }

    /**
     * 断开连接
     */
    private void disConnect() {
        socket.disConnect();
    }
}
