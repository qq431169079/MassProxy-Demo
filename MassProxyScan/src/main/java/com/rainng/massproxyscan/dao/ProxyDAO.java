package com.rainng.massproxyscan.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rainng.massproxyscan.dao.mapper.ProxyMapper;
import com.rainng.massproxyscan.models.entity.ProxyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProxyDAO {
    @Autowired
    private ProxyMapper mapper;

    public int insert(ProxyEntity entity) {
        if (containsByIp(entity.getIp())) {
            return 0;
        }

        try {
            return mapper.insert(entity);
        } catch (DuplicateKeyException ex) {
            return 0;
        }
    }

    public boolean containsByIp(String ip) {
        LambdaQueryWrapper<ProxyEntity> query = new LambdaQueryWrapper<>();
        query.select(ProxyEntity::getIp)
                .eq(ProxyEntity::getIp, ip);

        return mapper.selectOne(query) != null;
    }

    public void update(ProxyEntity entity) {
        mapper.updateById(entity);
    }

    public Integer getMaxId() {
        LambdaQueryWrapper<ProxyEntity> query = new LambdaQueryWrapper<>();
        query.select(ProxyEntity::getId).orderByDesc(ProxyEntity::getId).last("LIMIT 1");

        ProxyEntity proxy = mapper.selectOne(query);
        if (proxy == null) {
            return 0;
        }
        return proxy.getId();
    }

    public List<ProxyEntity> listByIdRange(Integer startId, Integer endId) {
        LambdaQueryWrapper<ProxyEntity> query = new LambdaQueryWrapper<>();
        query.ge(ProxyEntity::getId, startId).lt(ProxyEntity::getId, endId);
        return mapper.selectList(query);
    }
}
