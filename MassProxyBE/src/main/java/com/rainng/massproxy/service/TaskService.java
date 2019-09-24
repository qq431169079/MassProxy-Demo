package com.rainng.massproxy.service;

import com.rainng.massproxy.manager.TaskManager;
import com.rainng.massproxy.models.dto.ResultDTO;
import com.rainng.massproxy.models.dto.ScanTaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskService extends BaseService {
    @Autowired
    private TaskManager manager;

    public ResultDTO addScanTask(ScanTaskDTO taskDTO) {
        String task = taskDTO.getTask();
        int part = taskDTO.getPart();
        String[] tasks = new String[part];
        for (int i = 1; i <= part; i++) {
            tasks[i - 1] = String.format("%s --shard %d/%d", task, i, part);
        }

        return result(manager.addScanTask(tasks));
    }

    public ResultDTO addCrawlTask(String url, Integer start, Integer end) {
        String[] tasks = new String[end - start + 1];
        for (int i = start; i <= end; i++) {
            tasks[i - start] = String.format(url, Integer.toString(i));
        }
        return result(manager.addCrawlTask(tasks));
    }

    public ResultDTO addTestTask(String data) {
        String[] tasks = data.split("\r\n|\r|\n");
        return result(manager.addTestTask(tasks));
    }

    public ResultDTO getDashboardData() {
        return result(manager.getDashboardData());
    }
}
