package com.rainng.massproxyscan.dao.redis;

import com.rainng.massproxyscan.config.VerifyConfig;
import org.springframework.stereotype.Repository;

@Repository
public class VerifyIndexDAO extends BaseRedisDAO {
    /**
     * 获取下一个下标
     *
     * @return 下一个下标
     */
    public Long getNextIndex() {
        return increase(VerifyConfig.INDEX_NAME, VerifyConfig.STEP_LENGTH) - VerifyConfig.STEP_LENGTH;
    }

    /**
     * 置零下标
     */
    public void resetNext() {
        set(VerifyConfig.INDEX_NAME, "0");
    }

    /**
     * 获取代理表最大下标
     *
     * @return 最大下标
     */
    public Long getMaxIndex() {
        String maxString = get(VerifyConfig.INDEX_MAX);
        if (maxString == null || "".equals(maxString)) {
            return null;
        }
        return Long.valueOf(maxString);
    }

    /**
     * 设置代理表最大下标
     *
     * @param maxIndex 最大值
     */
    public void setMaxIndex(Long maxIndex) {
        set(VerifyConfig.INDEX_MAX, maxIndex.toString());
    }
}
