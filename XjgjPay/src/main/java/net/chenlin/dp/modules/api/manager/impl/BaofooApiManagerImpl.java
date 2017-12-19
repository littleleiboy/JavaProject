package net.chenlin.dp.modules.api.manager.impl;

import net.chenlin.dp.common.utils.HttpUtils;
import net.chenlin.dp.common.utils.JacksonUtils;
import net.chenlin.dp.modules.api.manager.BaofooApiManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("baofooApiManager")
public class BaofooApiManagerImpl implements BaofooApiManager {

    @Value("${myprop.api-baofoo-url}")
    private String apiUrl;

    /**
     * 宝付认证支付接口
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> backTrans(Map<String, String> map) throws Exception {
        String r = HttpUtils.postRequestSSL(apiUrl, JacksonUtils.beanToJson(map));
        if (!r.isEmpty())
            return JacksonUtils.jsonToMap(r);
        else
            return null;
    }
}
