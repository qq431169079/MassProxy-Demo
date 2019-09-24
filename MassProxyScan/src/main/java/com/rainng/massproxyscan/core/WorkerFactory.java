package com.rainng.massproxyscan.core;

import com.rainng.massproxyscan.core.acc.AccWorker;
import com.rainng.massproxyscan.core.crawl.CrawlWorker;
import com.rainng.massproxyscan.core.fast.FastWorker;
import com.rainng.massproxyscan.core.mass.MassWorker;
import com.rainng.massproxyscan.core.verify.VerifyWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 工作器工厂
 */
@Component
public class WorkerFactory {
    @Autowired
    private MassWorker massWorker;
    @Autowired
    private FastWorker fastWorker;
    @Autowired
    private AccWorker accWorker;
    @Autowired
    private VerifyWorker verifyWorker;
    @Autowired
    private CrawlWorker crawlWorker;

    /**
     * 根据参数获取工作器实例
     *
     * @param arg 参数
     * @return 工作器实例
     */
    public IWorker getInstance(String arg) {
        switch (arg) {
            case "mass":
                return massWorker;
            case "fast":
                return fastWorker;
            case "acc":
                return accWorker;
            case "verify":
                return verifyWorker;
            case "crawl":
                return crawlWorker;
        }

        return null;
    }
}
