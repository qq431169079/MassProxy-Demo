package com.rainng.massproxy.models.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 代理实体
 */
@Data
@TableName("ph_proxy")
public class ProxyEntity {
    /**
     * 代理Id
     */
    @TableId(value = "proxy_id", type = IdType.AUTO)
    private Integer id;

    /**
     * Ip
     */
    @TableField("proxy_ip")
    private String ip;

    /**
     * 端口
     */
    @TableField("proxy_port")
    private Integer port;

    /**
     * 类型(ProxyType)
     */
    @TableField("proxy_type")
    private Integer type;

    /**
     * 匿名度
     */
    @TableField("proxy_anonymity")
    private Boolean anonymity;

    /**
     * 国家Id
     */
    @TableField("proxy_country_id")
    private Integer countryId;

    /**
     * 区域Id
     */
    @TableField("proxy_region_id")
    private Integer regionId;

    /**
     * 城市Id
     */
    @TableField("proxy_city_id")
    private Integer cityId;

    /**
     * 连接时间
     */
    @TableField("proxy_connect_time")
    private Integer connectTime;

    /**
     * 响应时间
     */
    @TableField("proxy_response_time")
    private Integer responseTime;

    /**
     * 评分
     */
    @TableField("proxy_score")
    private Integer score = 20;

    /**
     * 上次检查时间
     */
    @TableField("proxy_check_time")
    private Date lastCheckTime = new Date();

    /**
     * 入库时间
     */
    @TableField("proxy_record_time")
    private Date recordTime = new Date();
}
