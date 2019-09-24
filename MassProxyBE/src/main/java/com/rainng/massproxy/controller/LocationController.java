package com.rainng.massproxy.controller;

import com.rainng.massproxy.models.dto.ResultDTO;
import com.rainng.massproxy.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 地理位置控制器
 */
@RestController
@RequestMapping("/location")
public class LocationController extends BaseController {
    @Autowired
    private LocationService service;

    /**
     * 列出可用国家
     *
     * @return 可用国家
     */
    @RequestMapping("listAvailableCountry")
    public ResultDTO listAvailableCountry() {
        return service.listAvailableCountry();
    }

    /**
     * 列出可用区域
     *
     * @param region 可用区域
     * @return
     */
    @RequestMapping("listAvailableRegion")
    public ResultDTO listAvailableRegion(String region) {
        return service.listAvailableRegion(region);
    }

    /**
     * 获取IP详细信息
     *
     * @param ip ip
     * @return IP详细信息
     */
    @RequestMapping("getIpDetails")
    public ResultDTO getIpDetails(String ip) {
        return service.getIpDetails(ip);
    }
}
