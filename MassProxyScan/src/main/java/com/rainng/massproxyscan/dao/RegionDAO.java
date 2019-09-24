package com.rainng.massproxyscan.dao;

import com.rainng.massproxyscan.dao.mapper.RegionMapper;
import com.rainng.massproxyscan.models.entity.RegionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RegionDAO {
    @Autowired
    private RegionMapper mapper;

    public int insert(RegionEntity region) {
        return mapper.insert(region);
    }

    public RegionEntity getById(int id) {
        return mapper.selectById(id);
    }
}
