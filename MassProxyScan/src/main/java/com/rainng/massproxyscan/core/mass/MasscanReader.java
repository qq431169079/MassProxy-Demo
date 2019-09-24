package com.rainng.massproxyscan.core.mass;

import com.rainng.massproxyscan.config.MassConfig;
import com.rainng.massproxyscan.config.QueueConfig;
import com.rainng.massproxyscan.dao.redis.QueueDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Masscan读取器
 */
@Component
public class MasscanReader {
    @Autowired
    private QueueDAO queueDAO;
    @Autowired
    private MasscanParser masscanParser;

    /**
     * 读取扫描结果并写入Redis队列
     */
    public void readToRedis() {
        readToRedis(MassConfig.RESULT_FILE_NAME);
    }

    /**
     * 读取扫描结果并写入Redis队列
     *
     * @param path 文件路径
     */
    public void readToRedis(String path) {
        try {
            List<String> proxies = masscanParser.parse(path);
            splitWriteToRedis(proxies);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void splitWriteToRedis(List<String> proxies) {
        List<String> list = new ArrayList<>(Math.min(QueueConfig.OPEN_PER_PUSH, proxies.size()));

        for (String proxy : proxies) {
            list.add(proxy);
            if (list.size() >= QueueConfig.OPEN_PER_PUSH) {
                writeToRedis(list);
            }
        }

        writeToRedis(list);
    }

    private void writeToRedis(List<String> proxies) {
        String[] arr = new String[proxies.size()];
        proxies.toArray(arr);
        queueDAO.pushOpenList(arr);
        queueDAO.increaseTaskCount(arr.length);
        proxies.clear();
    }
}
