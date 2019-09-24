package com.rainng.massproxy.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rainng.massproxy.dao.mapper.RangeMapper;
import com.rainng.massproxy.models.entity.RangeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RangeDAO {
    @Autowired
    private RangeMapper mapper;

    public int insert(RangeEntity range) {
        return mapper.insert(range);
    }

    public void insert(List<RangeEntity> ranges) {
        for (RangeEntity range : ranges) {
            mapper.insert(range);
        }
    }

    public RangeEntity select(long ipCode) {
        LambdaQueryWrapper<RangeEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(RangeEntity::getCityId, RangeEntity::getLatitude, RangeEntity::getLongitude)
                .ge(RangeEntity::getIpTo, ipCode)
                .orderByAsc(RangeEntity::getIpTo)
                .last("LIMIT 1");

        return mapper.selectList(wrapper).get(0);
    }
}
