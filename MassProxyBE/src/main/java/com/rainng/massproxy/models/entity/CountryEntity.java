package com.rainng.massproxy.models.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 国家实体
 */
@Data
@TableName("ph_loc_country")
public class CountryEntity {
    /**
     * 国家Id
     */
    @TableId(value = "country_id", type = IdType.AUTO)
    private Integer id;

    /**
     * 国家代码
     */
    @TableField("country_code")
    private String code;

    /**
     * 国家名称
     */
    @TableField("country_name")
    private String name;

    public CountryEntity() {

    }

    public CountryEntity(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
