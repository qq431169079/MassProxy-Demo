package com.rainng.massproxy.controller;

import com.rainng.massproxy.models.dto.CrawlTaskDTO;
import com.rainng.massproxy.models.dto.DataDTO;
import com.rainng.massproxy.models.dto.ResultDTO;
import com.rainng.massproxy.models.dto.ScanTaskDTO;
import com.rainng.massproxy.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("task")
public class TaskController extends BaseController {
    @Autowired
    private TaskService service;

    /**
     * 添加扫描任务
     *
     * @param task 任务
     * @return 当前任务数
     */
    @RequestMapping("addScanTask")
    public ResultDTO addScanTask(@RequestBody ScanTaskDTO task) {
        if (task.getPart() == null) {
            task.setPart(1);
        }
        return service.addScanTask(task);
    }

    /**
     * 添加爬虫任务
     *
     * @param task 任务
     * @return 当前任务数
     */
    @RequestMapping("addCrawlTask")
    public ResultDTO addCrawlTask(@RequestBody CrawlTaskDTO task) {
        if (task.getStart() == null) {
            task.setStart(1);
        }
        if (task.getEnd() == null) {
            task.setEnd(1);
        }
        return service.addCrawlTask(task.getUrl(), task.getStart(), task.getEnd());
    }

    /**
     * 添加代理到测试队列
     *
     * @param data 数据
     * @return 当前任务数
     */
    @RequestMapping("addTestTask")
    public ResultDTO addTestTask(@RequestBody DataDTO data) {
        return service.addTestTask(data.getData());
    }

    /**
     * 获取Dashboard数据
     * @return Dashboard数据
     */
    @RequestMapping("getDashboardData")
    public ResultDTO getDashboardData() {
        return service.getDashboardData();
    }
}
