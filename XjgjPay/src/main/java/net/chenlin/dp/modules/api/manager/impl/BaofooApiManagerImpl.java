package net.chenlin.dp.modules.api.manager.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("baofooApiManager")
public class BaofooApiManagerImpl {

    @Value("${myprop.api-baofoo-url}")
    private String urlPerfix;

    private String getApiUrl(String method) {
        return urlPerfix + method;
    }

    //TODO 预绑卡交易

}
