package net.chenlin.dp.modules.api.manager.impl;

import net.chenlin.dp.common.constant.XjgjAccountApiConstant;
import net.chenlin.dp.common.utils.HttpUtils;
import net.chenlin.dp.modules.api.manager.XjgjAccountApiManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 西郊国际结算系统API管理
 */
@Component("xjgjAccountApiManager")
public class XjgjAccountApiManagerImpl implements XjgjAccountApiManager {

    @Value("${myprop.api-jsxt-app-key}")
    private String accApiToken;

    private String getApiUrl(String method) {
        return accApiToken + method;
    }

    /**
     * 通过手机号查询会员绑定基本信息
     *
     * @param str 发送数据字符串
     * @return 返回结果字符串
     */
    @Override
    public String getMemberBindingInfo(String str) {
        return HttpUtils.postRequestSSL(getApiUrl(XjgjAccountApiConstant.METHOD_GET_MEMBER_BINDING_INFO), str);
    }

}
