package com.rainng.massproxyscan;

import com.rainng.massproxyscan.core.WorkerFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.rainng.massproxyscan.dao.mapper")
public class Application implements CommandLineRunner {

    @Autowired
    private WorkerFactory workerFactory;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String arg = checkArgs(args);
        workerFactory.getInstance(arg).run();
    }

    /**
     * 检查运行参数
     *
     * @param args 运行参数
     * @return 对应Worker名
     * @throws Exception 参数错误
     */
    private String checkArgs(String[] args) throws Exception {
        if (args.length == 1) {
            String arg = args[0].toLowerCase();
            if ("mass".equals(arg) || "fast".equals(arg) || "acc".equals(arg) || "verify".equals(arg) || "crawl".equals(arg)) {
                return arg;
            }
        }
        throw new Exception("Acceptable parameters:\r\n" +
                "mass->Masscan\r\nfast->Fast Test\r\n" +
                "accurate->Accurate Test\r\nverify->Long Verify\r\n" +
                "crawl->Crawler");
    }
}
