package net.chenlin.dp.modules.api.manager.impl;

import net.chenlin.dp.common.constant.BaofooApiConstant;
import net.chenlin.dp.common.constant.SystemConstant;
import net.chenlin.dp.common.utils.*;
import net.chenlin.dp.modules.api.manager.BaofooApiManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("baofooApiManager")
public class BaofooApiManagerImpl implements BaofooApiManager {

    @Value("${myprop.api.baofoo.url}")
    private String apiUrl;

    /**
     * 宝付认证支付接口
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public String backTrans(Map<String, String> map) throws Exception {
        return HttpUtils.postRequestSSL(apiUrl, JacksonUtils.beanToJson(map));
    }
}
