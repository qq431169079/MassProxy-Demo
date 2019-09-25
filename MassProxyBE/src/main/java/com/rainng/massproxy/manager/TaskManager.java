package com.rainng.massproxy.manager;

import com.rainng.massproxy.dao.redis.QueueDAO;
import com.rainng.massproxy.models.dto.DashboardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TaskManager extends BaseManager {
    @Autowired
    private QueueDAO queueDAO;

    public Integer addScanTask(String[] tasks) {
        queueDAO.pushTaskList(tasks);

        return queueDAO.getTaskLength();
    }

    public Integer addTestTask(String[] tasks) {
        queueDAO.pushOkList(tasks);
        return queueDAO.getOkLength();
    }

    public Integer addCrawlTask(String[] tasks) {
        queueDAO.pushCrawlList(tasks);
        return queueDAO.getCrawlLength();
    }

    private static int lastTask = 0;
    private static int lastOpen = 0;
    private static int lastOk = 0;
    private static int lastCrawl = 0;
    private static Date lastTime = null;

    public DashboardDTO getDashboardData() {
        double interval;
        if (lastTime == null) {
            interval = Integer.MAX_VALUE;
        } else {
            interval = new Date().getTime() - lastTime.getTime();
            interval /= 1000;
        }
        lastTime = new Date();

        int taskSize = queueDAO.getTaskLength();
        int taskCount = queueDAO.getTaskCount().intValue();
        int taskSpeed = (int) ((taskCount - lastTask) / interval);
        lastTask = taskCount;

        int openSize = queueDAO.getOpenLength();
        int openCount = queueDAO.getOpenCount().intValue();
        int openSpeed = (int) ((openCount - lastOpen) / interval);
        lastOpen = openCount;

        int okSize = queueDAO.getOkLength();
        int okCount = queueDAO.getOkCount().intValue();
        int okSpeed = (int) ((okCount - lastOk) / interval);
        lastOk = okCount;

        int crawlSize = queueDAO.getCrawlLength();
        int crawlCount = queueDAO.getCrawlCount().intValue();
        int crawlSpeed = (int) ((crawlCount - lastCrawl) / interval);
        lastCrawl = crawlCount;

        String taskNext = queueDAO.lookTask();
        String fastNext = queueDAO.lookFast();
        String accNext = queueDAO.lookAcc();
        String crawlNext = queueDAO.lookCrawl();

        return new DashboardDTO(taskSize, taskCount, taskSpeed,
                openSize, openCount, openSpeed,
                okSize, okCount, okSpeed,
                crawlSize, crawlCount, crawlSpeed,
                taskNext, fastNext, accNext, crawlNext);
    }
}
