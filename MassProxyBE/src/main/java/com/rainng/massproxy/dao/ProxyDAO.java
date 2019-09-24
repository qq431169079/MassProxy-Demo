package com.rainng.massproxy.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rainng.massproxy.dao.mapper.ProxyMapper;
import com.rainng.massproxy.models.constant.ProxyType;
import com.rainng.massproxy.models.constant.QueryOrder;
import com.rainng.massproxy.models.entity.ProxyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ProxyDAO {
    public static final int PAGE_SIZE = 30;
    private static final int MOST_COUNTRY_COUNT = 5;
    private static final int RECENTLY_COUNT = 4;
    @Autowired
    private ProxyMapper mapper;

    public List<ProxyEntity> query(
            Integer pageIndex,
            Integer port, Integer type, Integer countryId,
            Integer maxConnTime, Integer maxRespTime, Integer minScore, Integer order) {

        LambdaQueryWrapper<ProxyEntity> query =
                createQuery(port, type, countryId, maxConnTime, maxRespTime, minScore, order);

        if (pageIndex == null) {
            pageIndex = 1;
        }

        query.last(String.format("LIMIT %d,%d", (pageIndex - 1) * PAGE_SIZE, PAGE_SIZE));
        return mapper.selectList(query);
    }

    public List<ProxyEntity> queryNoLimit(Integer port, Integer type, Integer countryId,
                                          Integer maxConnTime, Integer maxRespTime, Integer minScore, Integer order) {
        return mapper.selectList(createQuery(port, type, countryId, maxConnTime, maxRespTime, minScore, order));
    }

    public Integer queryCount(
            Integer port, Integer type,
            Integer countryId,
            Integer maxConnTime, Integer maxRespTime, Integer minScore) {

        return mapper.selectCount(
                createQuery(port, type, countryId, maxConnTime, maxRespTime, minScore, 0));
    }

    private LambdaQueryWrapper<ProxyEntity> createQuery(Integer port, Integer type, Integer countryId, Integer maxConnTime, Integer maxRespTime, Integer minScore, Integer order) {
        LambdaQueryWrapper<ProxyEntity> query = new LambdaQueryWrapper<>();
        if (port != null) {
            query.eq(ProxyEntity::getPort, port);
        }
        if (type != null) {
            query.eq(ProxyEntity::getType, type);
        }
        if (countryId != null) {
            query.eq(ProxyEntity::getCountryId, countryId);
        }
        if (maxConnTime != null) {
            query.le(ProxyEntity::getConnectTime, maxConnTime);
        }
        if (maxRespTime != null) {
            query.le(ProxyEntity::getResponseTime, maxRespTime);
        }
        if (minScore != null) {
            query.ge(ProxyEntity::getScore, minScore);
        }

        if (order == null) {
            order = 0;
        }

        if (order.equals(QueryOrder.CONN_TIME)) {
            query.orderByAsc(ProxyEntity::getConnectTime);
        } else if (order.equals(QueryOrder.RESP_TIME)) {
            query.orderByAsc(ProxyEntity::getResponseTime);
        } else if (order.equals(QueryOrder.SCORE)) {
            query.orderByDesc(ProxyEntity::getScore);
        } else if (order.equals(QueryOrder.OVERALL)) {
            query.orderByDesc(ProxyEntity::getScore)
                    .orderByAsc(ProxyEntity::getResponseTime);
        } else {
            query.orderByDesc(ProxyEntity::getId);
        }

        return query;
    }

    public List<Map<String, Object>> countByCountry() {
        QueryWrapper<ProxyEntity> query = new QueryWrapper<>();
        query.select("proxy_country_id as countryId, COUNT(*) as count")
                .groupBy("proxy_country_id")
                .orderByDesc("count");

        return mapper.selectMaps(query);
    }

    public List<Map<String, Object>> listMostCountry() {
        QueryWrapper<ProxyEntity> query = new QueryWrapper<>();
        query.select("proxy_country_id as countryId, COUNT(*) as count")
                .groupBy("proxy_country_id")
                .orderByDesc("count")
                .last("LIMIT " + MOST_COUNTRY_COUNT);

        return mapper.selectMaps(query);
    }

    public Integer countHttp() {
        LambdaQueryWrapper<ProxyEntity> query = new LambdaQueryWrapper<>();
        query.eq(ProxyEntity::getType, ProxyType.HTTP);
        return mapper.selectCount(query);
    }

    public Integer countHttps() {
        LambdaQueryWrapper<ProxyEntity> query = new LambdaQueryWrapper<>();
        query.eq(ProxyEntity::getType, ProxyType.HTTPS);
        return mapper.selectCount(query);
    }

    public Integer countBoth() {
        LambdaQueryWrapper<ProxyEntity> query = new LambdaQueryWrapper<>();
        query.eq(ProxyEntity::getType, ProxyType.BOTH);
        return mapper.selectCount(query);
    }

    public List<ProxyEntity> listStable() {
        LambdaQueryWrapper<ProxyEntity> query = new LambdaQueryWrapper<>();
        query.select(ProxyEntity::getIp, ProxyEntity::getPort, ProxyEntity::getType, ProxyEntity::getResponseTime, ProxyEntity::getScore)
                .eq(ProxyEntity::getType, ProxyType.BOTH)
                .orderByDesc(ProxyEntity::getScore)
                .orderByAsc(ProxyEntity::getResponseTime)
                .last("LIMIT " + RECENTLY_COUNT);

        return mapper.selectList(query);
    }

    public List<ProxyEntity> listCountryId() {
        QueryWrapper<ProxyEntity> query = new QueryWrapper<>();
        query.select("DISTINCT proxy_country_id as countryId")
                .groupBy("proxy_country_id")
                .orderByDesc("COUNT(*)");

        return mapper.selectList(query);
    }

    public List<ProxyEntity> listRegionIdByCountryId(Integer countryId) {
        QueryWrapper<ProxyEntity> query = new QueryWrapper<>();
        query.select("DISTINCT proxy_region_id as regionId")
                .eq("proxy_country_id", countryId)
                .groupBy("proxy_region_id")
                .orderByDesc("COUNT(*)");

        return mapper.selectList(query);
    }

    public ProxyEntity getByIp(String ip) {
        LambdaQueryWrapper<ProxyEntity> query = new LambdaQueryWrapper<>();
        query.eq(ProxyEntity::getIp, ip);

        return mapper.selectOne(query);
    }
}
