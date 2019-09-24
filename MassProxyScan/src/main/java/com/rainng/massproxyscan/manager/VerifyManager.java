package com.rainng.massproxyscan.manager;

import com.rainng.massproxyscan.config.VerifyConfig;
import com.rainng.massproxyscan.dao.ProxyDAO;
import com.rainng.massproxyscan.dao.redis.VerifyIndexDAO;
import com.rainng.massproxyscan.models.entity.ProxyEntity;
import com.rainng.massproxyscan.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class VerifyManager {
    @Autowired
    private ProxyDAO proxyDAO;

    @Autowired
    private VerifyIndexDAO verifyIndexDAO;

    /**
     * 获取待检测Proxy列表
     *
     * @return 待检测Proxy列表
     */
    public List<ProxyEntity> getNextProxyList() {
        Pair<Long, Long> range = getNextRange();
        Integer startIndex = range.getFirst().intValue();
        Integer endIndex = range.getSecond().intValue();

        List<ProxyEntity> list = proxyDAO.listByIdRange(startIndex, endIndex);
        // 防止用户删除Proxy表记录导致MaxIndex无法更新
        if (endIndex - startIndex > 0 && list.size() == 0) {
            flushMaxIndex();
        }

        return list;
    }

    public void updateProxy(ProxyEntity entity) {
        entity.setLastCheckTime(new Date());
        proxyDAO.update(entity);
    }

    /**
     * 获取下一个检测区间
     *
     * @return 检测ProxyId区间
     */
    private Pair<Long, Long> getNextRange() {
        final int step = VerifyConfig.STEP_LENGTH;

        Long index = verifyIndexDAO.getNextIndex();
        Long max = verifyIndexDAO.getMaxIndex();

        // Proxy表计数缓存不存在
        if (max == null || max <= 0) {
            max = flushMaxIndex();
            if (max == 0) {
                return new Pair<>(0L, 0L);
            }
        }

        // index将越出表边界计数缓存
        if (index + step >= max) {
            max = flushMaxIndex();
        }

        Long endIndex = index + step;
        if (endIndex >= max) {
            endIndex = max;
            verifyIndexDAO.resetNext();
        }

        return new Pair<>(index, endIndex);
    }

    /**
     * 刷新Proxy表计数缓存
     *
     * @return 刷新后的Proxy表计数
     */
    private Long flushMaxIndex() {
        Long maxIndex = proxyDAO.getMaxId().longValue() + 1;
        verifyIndexDAO.setMaxIndex(maxIndex);
        return maxIndex;
    }
}
