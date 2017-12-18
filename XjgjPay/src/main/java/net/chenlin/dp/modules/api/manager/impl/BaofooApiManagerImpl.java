package net.chenlin.dp.modules.api.manager.impl;

import net.chenlin.dp.common.constant.BaofooApiConstant;
import net.chenlin.dp.common.utils.HttpUtils;
import net.chenlin.dp.common.utils.JSONUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("baofooApiManager")
public class BaofooApiManagerImpl {

    @Value("${myprop.api-baofoo-url}")
    private String apiUrl;

    //TODO 预绑卡交易
    public Map<String, Object> backTrans(Map<String, Object> map) throws Exception {
        String r = HttpUtils.postRequestSSL(apiUrl, JSONUtils.beanToJson(map))
        if (!r.isEmpty())
            return JSONUtils.jsonToMap(r);
        else
            return null;
    }
}
