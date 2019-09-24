package com.rainng.massproxyscan.util;

import com.rainng.massproxyscan.config.FastConfig;
import lombok.Getter;
import lombok.Setter;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * 用于简化代理测试的简单Socket, 收发数据为String, 不会抛出异常
 */
public class SimpleSocket {
    @Getter
    private Socket socket = null;

    @Getter
    @Setter
    private int connectTimeout = FastConfig.CONNECT_TIMEOUT;

    @Getter
    private int soTimeout = FastConfig.SEND_READ_TIMEOUT;

    @Getter
    @Setter
    private long lastSendTime = -1;

    /**
     * 设置内部Socket发送/接收的超时时间
     *
     * @param timeout 超时时间
     * @return 是否设置成功
     */
    public boolean setSoTimeout(int timeout) {
        soTimeout = timeout;

        if (socket == null || socket.isClosed()) {
            return true;
        }

        try {
            socket.setSoTimeout(timeout);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    /**
     * 连接目标地址
     *
     * @param address 地址
     * @return 是否连接成功
     */
    public boolean connect(String address) {
        disConnect();

        socket = new Socket();
        setSoTimeout(soTimeout);

        try {
            socket.connect(parseAddress(address), connectTimeout);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    /**
     * 断开与目标主机的连接
     */
    public void disConnect() {
        if (socket == null) {
            return;
        }

        try {
            tryClose(socket.getOutputStream());
            tryClose(socket.getInputStream());
            tryClose(socket);
        } catch (Exception ex) {
        }

        socket = null;
    }

    /**
     * 发送String数据
     *
     * @param data 数据
     * @return 是否发送成功
     */
    public boolean send(String data) {
        try {
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(data.getBytes());
            lastSendTime = System.currentTimeMillis();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    /**
     * 接收数据
     *
     * @return 接收到的数据
     */
    public String receive() {
        return receive(16);
    }

    /**
     * 接收数据
     *
     * @param length 要接收的数据长度
     * @return 接收到的数据
     */
    public String receive(int length) {
        byte[] buffer = new byte[length];
        try {
            InputStream inputStream = socket.getInputStream();
            inputStream.read(buffer);
            return new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            return "";
        }
    }

    /**
     * 解析IP:Port格式的地址为SocketAddress
     *
     * @param address 地址
     * @return SocketAddress
     */
    private SocketAddress parseAddress(String address) {
        String[] split = address.split(":");
        return new InetSocketAddress(split[0], Integer.parseInt(split[1]));
    }

    private IOException tryClose(Closeable target) {
        try {
            target.close();
            return null;
        } catch (IOException ex) {
            return ex;
        }
    }
}
