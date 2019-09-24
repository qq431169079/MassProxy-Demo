package com.rainng.massproxyscan.models.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 城市实体
 */
@Data
@TableName("ph_loc_city")
public class CityEntity {
    /**
     * 城市Id
     */
    @TableId(value = "city_id", type = IdType.AUTO)
    private Integer id;

    /**
     * 省份/区域Id
     */
    @TableField("city_region_id")
    private Integer regionId;

    /**
     * 城市名
     */
    @TableField("city_name")
    private String name;

    public CityEntity() {

    }

    public CityEntity(Integer regionId, String name) {
        this.regionId = regionId;
        this.name = name;
    }
}
