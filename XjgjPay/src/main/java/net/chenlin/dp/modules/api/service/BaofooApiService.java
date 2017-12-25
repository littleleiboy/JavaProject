package net.chenlin.dp.modules.api.service;

import java.util.Map;

public interface BaofooApiService {
    /**
     * 宝付支付接口调用
     *
     * @param map
     * @return
     * @throws Exception
     */
    Map<String, Object> backTrans(Map<String, String> map) throws Exception;
}
