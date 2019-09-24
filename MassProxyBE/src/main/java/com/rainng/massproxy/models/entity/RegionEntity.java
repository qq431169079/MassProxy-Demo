package com.rainng.massproxy.models.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 省份/区域实体
 */
@Data
@TableName("ph_loc_region")
public class RegionEntity {
    /**
     * 省份/区域Id
     */
    @TableId(value = "region_id", type = IdType.AUTO)
    private Integer id;

    /**
     * 国家Id
     */
    @TableField("region_country_id")
    private Integer countryId;

    /**
     * 省份/区域名称
     */
    @TableField("region_name")
    private String name;

    public RegionEntity() {
    }

    public RegionEntity(Integer countryId, String name) {
        this.countryId = countryId;
        this.name = name;
    }
}
