package com.rainng.massproxyscan.models.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Ip范围实体
 */
@Data
@TableName("ph_loc_range")
public class RangeEntity {
    /**
     * 范围Id
     */
    @TableId(value = "range_id", type = IdType.AUTO)
    private Integer id;

    /**
     * 范围开始值
     */
    @TableField("range_from")
    private Long ipFrom;

    /**
     * 范围结束值
     */
    @TableField("range_to")
    private Long ipTo;

    /**
     * 城市Id
     */
    @TableField("range_city_id")
    private Integer cityId;

    @TableField("range_latitude")
    private Double latitude;

    @TableField("range_longitude")
    private Double longitude;

    public RangeEntity() {

    }

    public RangeEntity(Long ipFrom, Long ipTo, Integer cityId, Double latitude, Double longitude) {
        this.ipFrom = ipFrom;
        this.ipTo = ipTo;
        this.cityId = cityId;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}