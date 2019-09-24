package com.rainng.massproxy.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rainng.massproxy.dao.mapper.RegionMapper;
import com.rainng.massproxy.models.entity.RegionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RegionDAO {
    @Autowired
    private RegionMapper mapper;

    public RegionEntity getRegionById(int id) {
        return mapper.selectById(id);
    }

    public String getNameById(Integer id) {
        LambdaQueryWrapper<RegionEntity> query = new LambdaQueryWrapper<>();
        query.select(RegionEntity::getName)
                .eq(RegionEntity::getId, id);

        return mapper.selectOne(query).getName();
    }
}
