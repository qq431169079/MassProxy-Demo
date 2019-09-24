package com.rainng.massproxyscan.core.acc;

import com.rainng.massproxyscan.config.AccConfig;
import com.rainng.massproxyscan.models.constant.ProxyType;

import java.io.InputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * 精确代理验证器
 */
public class AccProxyTester {
    /**
     * 测试n轮Http与Https可用性, 必须全部通过
     *
     * @param proxy 代理
     * @return 测试结果
     */
    public AccTestResult testAll(String proxy, int round) {
        AccTestResult result = null;
        for (int i = 0; i < round; i++) {
            result = testAll(proxy);
            if (result.getType() == ProxyType.BAD) {
                return result;
            }
        }

        return result;
    }

    /**
     * 测试Http与Https可用性
     *
     * @param proxy 代理
     * @return 测试结果
     */
    public AccTestResult testAll(String proxy) {
        AccTestResult result = new AccTestResult();
        result.setIp(parseIp(proxy));
        result.setPort(parsePort(proxy));

        testHttp(result);
        testHttps(result);

        return result;
    }

    private void testHttp(AccTestResult result) {
        test(AccConfig.HTTP_URL, AccConfig.HTTP_EQUALS, result);
    }

    private void testHttps(AccTestResult result) {
        test(AccConfig.HTTPS_URL, AccConfig.HTTPS_EQUALS, result);
    }

    private void test(String testUrl, String part, AccTestResult result) {
        try {
            innerTest(testUrl, part, result);
        } catch (Exception ex) {
        }
    }

    private void innerTest(String testUrl, String part, AccTestResult result) throws Exception {
        URL url = new URL(testUrl);

        Proxy proxy = createProxy(result.getIp(), result.getPort());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxy);
        conn.setConnectTimeout(AccConfig.CONNECT_TIMEOUT);
        conn.setReadTimeout(AccConfig.READ_TIMEOUT);

        long time = System.currentTimeMillis();
        conn.connect();
        result.setMinimalConnectTime(System.currentTimeMillis() - time);
        time = System.currentTimeMillis();

        StringBuilder sb = new StringBuilder();
        try (InputStream stream = conn.getInputStream()) {
            result.setMinimalResponseTime(System.currentTimeMillis() - time);

            byte[] buffer = new byte[1024];
            int len;
            while ((len = stream.read(buffer)) > 0) {
                sb.append(new String(buffer, 0, len, StandardCharsets.UTF_8));
            }
        }

        if (sb.toString().equals(part)) {
            if (testUrl.startsWith("https")) {
                result.httpsAvailable();
            } else {
                result.httpAvailable();
            }
        }
    }

    private Proxy createProxy(String ip, int port) {
        SocketAddress address = new InetSocketAddress(ip, port);
        return new Proxy(Proxy.Type.HTTP, address);
    }

    private String parseIp(String proxyAddress) {
        return proxyAddress.split(":")[0];
    }

    private int parsePort(String proxyAddress) {
        return Integer.parseInt(proxyAddress.split(":")[1]);
    }
}