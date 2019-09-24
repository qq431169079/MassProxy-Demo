package com.rainng.massproxyscan.core.mass;

import com.rainng.massproxyscan.config.MassConfig;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Masscan运行器
 */
@Component
public class MasscanRunner {
    /**
     * 运行Masscan
     *
     * @param args 参数
     * @throws Exception 异常
     */
    public void run(String args) throws Exception {
        runMasscan(MassConfig.MASSCAN_ARGS + args);
    }

    /**
     * 恢复中断的扫描工作
     *
     * @throws Exception 异常
     */
    public void resume() throws Exception {
        runMasscan(MassConfig.MASSCAN_RESUME_ARGS);
    }

    /**
     * 检查是否需要恢复中断的工作
     *
     * @return 是否需要恢复
     */
    public boolean canResume() {
        return new File(MassConfig.PAUSE_FILE_NAME).exists();
    }

    /**
     * 删除Masscan扫描结果文件
     */
    public void deleteFile() {
        File result = new File(MassConfig.RESULT_FILE_NAME);
        File pause = new File(MassConfig.PAUSE_FILE_NAME);
        if (result.exists()) {
            result.delete();
        }
        if (pause.exists()) {
            pause.delete();
        }
    }

    private void runMasscan(String args) throws Exception {
        Process process = Runtime.getRuntime().exec(args);
        int a = process.waitFor();
        System.out.println(a);
    }
}
