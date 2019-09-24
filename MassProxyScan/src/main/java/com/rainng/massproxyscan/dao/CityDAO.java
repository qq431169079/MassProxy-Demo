package com.rainng.massproxyscan.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rainng.massproxyscan.dao.mapper.CityMapper;
import com.rainng.massproxyscan.models.entity.CityEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CityDAO {
    @Autowired
    private CityMapper mapper;

    public int insert(CityEntity city) {
        return mapper.insert(city);
    }

    public CityEntity getById(int id) {
        return mapper.selectById(id);
    }
}
