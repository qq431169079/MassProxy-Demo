package com.rainng.massproxy.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rainng.massproxy.dao.mapper.CountryMapper;
import com.rainng.massproxy.models.entity.CountryEntity;
import com.rainng.massproxy.models.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CountryDAO {
    @Autowired
    private CountryMapper mapper;

    public CountryEntity getCountryById(int id) {
        return mapper.selectById(id);
    }

    public Integer getIdByName(String name) throws EntityNotFoundException {
        LambdaQueryWrapper<CountryEntity> query = new LambdaQueryWrapper<>();
        query.select(CountryEntity::getId)
                .eq(CountryEntity::getName, name);

        CountryEntity country = mapper.selectOne(query);
        if (country == null) {
            throw new EntityNotFoundException("国家未找到");
        }

        return country.getId();
    }

    public String getNameById(Integer id) {
        LambdaQueryWrapper<CountryEntity> query = new LambdaQueryWrapper<>();
        query.select(CountryEntity::getName)
                .eq(CountryEntity::getId, id);

        return mapper.selectOne(query).getName();
    }

    public List<CountryEntity> listAllCountryName() {
        LambdaQueryWrapper<CountryEntity> query = new LambdaQueryWrapper<>();
        query.select(CountryEntity::getName);

        return mapper.selectList(query);
    }
}
