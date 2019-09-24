package com.rainng.massproxyscan.core.mass;

import com.rainng.massproxyscan.config.MassConfig;
import com.rainng.massproxyscan.core.IWorker;
import com.rainng.massproxyscan.dao.redis.QueueDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 大量扫描工作器
 */
@Component
public class MassWorker implements IWorker {
    @Autowired
    private QueueDAO queueDAO;

    @Autowired
    private MasscanRunner masscanRunner;
    @Autowired
    private MasscanReader masscanReader;

    @Override
    public void run() {
        checkResume();
        while (true) {
            try {
                checkTask();
                Thread.sleep(MassConfig.INTERVAL);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void checkResume() {
        if (!masscanRunner.canResume()) {
            return;
        }

        try {
            masscanRunner.resume();
            readToRedis();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void checkTask() throws Exception {
        if (queueDAO.getTaskLength() <= 0) {
            return;
        }

        String taskArgs = queueDAO.getTask();
        if (taskArgs == null || "".equals(taskArgs)) {
            return;
        }

        masscanRunner.run(taskArgs);
        readToRedis();
    }

    private void readToRedis() {
        masscanReader.readToRedis();
        masscanRunner.deleteFile();
    }
}
