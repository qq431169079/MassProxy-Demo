package com.rainng.massproxy.controller;

import com.rainng.massproxy.models.dto.ResultDTO;
import com.rainng.massproxy.service.ProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 代理控制器
 */
@RestController
@RequestMapping("/proxy")
public class ProxyController extends BaseController {
    @Autowired
    private ProxyService service;

    /**
     * 查询代理
     *
     * @param page        页号
     * @param port        端口
     * @param type        类型
     * @param country     国家
     * @param maxConnTime 最大连接时间
     * @param maxRespTime 最大响应时间
     * @param minScore    最小分数
     * @param order       排序方法
     * @return 代理列表
     */
    @RequestMapping("query")
    public ResultDTO query(Integer page,
                           Integer port, Integer type, String country,
                           Integer maxConnTime, Integer maxRespTime, Integer minScore, Integer order) {

        return service.query(page, port, type, country, maxConnTime, maxRespTime, minScore, order);
    }

    /**
     * 获取代理分页信息
     *
     * @param port        端口
     * @param type        类型
     * @param country     国家
     * @param maxConnTime 最大连接时间
     * @param maxRespTime 最大响应时间
     * @param minScore    最小分数
     * @return 代理分页信息
     */
    @RequestMapping("queryPageInfo")
    public ResultDTO queryPageInfo(
            Integer port, Integer type,
            String country,
            Integer maxConnTime, Integer maxRespTime, Integer minScore) {
        return service.queryPageInfo(port, type, country, maxConnTime, maxRespTime, minScore);
    }

    /**
     * 提取代理
     *
     * @param port        端口
     * @param type        类型
     * @param country     国家
     * @param maxConnTime 最大连接时间
     * @param maxRespTime 最大响应时间
     * @param minScore    最小分数
     * @param order       排序方法
     * @return 代理列表
     */
    @RequestMapping(value = "extract")
    public ResultDTO extract(Integer port, Integer type, String country,
                             Integer maxConnTime, Integer maxRespTime, Integer minScore, Integer order) {
        return service.extract(port, type, country, maxConnTime, maxRespTime, minScore, order);
    }

    /**
     * TCP Ping
     *
     * @param ip   IP
     * @param port 端口
     * @return 延迟
     */
    @RequestMapping("tcpPing")
    public ResultDTO tcpPing(String ip, Integer port) {
        return service.tcpPing(ip, port);
    }

    /**
     * 获取代理详细信息
     *
     * @param ip IP地址
     * @return 代理IP详细信息
     */
    @RequestMapping("getDetails")
    public ResultDTO getDetails(String ip) {
        return service.getDetails(ip);
    }

    /**
     * 通过类型统计
     *
     * @return 统计信息
     */
    @RequestMapping("countByType")
    public ResultDTO countByType() {
        return service.countByType();
    }

    /**
     * 通过国家统计
     *
     * @return 统计信息
     */
    @RequestMapping("countByCountry")
    public ResultDTO countByCountry() {
        return service.countByCountry();
    }

    /**
     * 智能推荐稳定代理
     *
     * @return 稳定代理列表
     */
    @RequestMapping("listStable")
    public ResultDTO listStable() {
        return service.listStable();
    }

    /**
     * 获取前几名国家
     *
     * @return 前几名国家
     */
    @RequestMapping("listMostCountry")
    public ResultDTO listMostCountry() {
        return service.listMostCountry();
    }
}
