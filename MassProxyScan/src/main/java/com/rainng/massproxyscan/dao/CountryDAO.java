package com.rainng.massproxyscan.dao;

import com.rainng.massproxyscan.dao.mapper.CountryMapper;
import com.rainng.massproxyscan.models.entity.CountryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CountryDAO {
    @Autowired
    private CountryMapper mapper;

    public int insert(CountryEntity country) {
        return mapper.insert(country);
    }

    public CountryEntity getById(int id) {
        return mapper.selectById(id);
    }
}
