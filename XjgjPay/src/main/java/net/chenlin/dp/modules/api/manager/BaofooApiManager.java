package net.chenlin.dp.modules.api.manager;

import java.util.Map;

public interface BaofooApiManager {
    /**
     * 宝付认证支付接口
     *
     * @param map
     * @return
     * @throws Exception
     */
    Map<String, Object> backTrans(Map<String, String> map) throws Exception;
}
