package com.rainng.massproxy.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rainng.massproxy.dao.mapper.CityMapper;
import com.rainng.massproxy.models.entity.CityEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CityDAO {
    @Autowired
    private CityMapper mapper;

    public CityEntity getCityById(int id) {
        return mapper.selectById(id);
    }

    public String getNameById(Integer id) {
        LambdaQueryWrapper<CityEntity> query = new LambdaQueryWrapper<>();
        query.select(CityEntity::getName)
                .eq(CityEntity::getId, id);

        return mapper.selectOne(query).getName();
    }
}
