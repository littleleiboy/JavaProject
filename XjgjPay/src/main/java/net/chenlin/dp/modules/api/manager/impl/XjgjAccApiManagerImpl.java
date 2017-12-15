package net.chenlin.dp.modules.api.manager.impl;

import net.chenlin.dp.common.constant.XjgjAccApiConstant;
import net.chenlin.dp.common.utils.DynamicTokenUtils;
import net.chenlin.dp.common.utils.HttpUtils;
import net.chenlin.dp.common.utils.JSONUtils;
import net.chenlin.dp.modules.api.manager.XjgjAccApiManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 西郊国际结算系统API管理
 */
@Component("xjgjAccApiManager")
public class XjgjAccApiManagerImpl implements XjgjAccApiManager {

    @Value("${myprop.api-jsxt-app-key}")
    private String accApiToken;

    @Value("${api-jsxt-app-url}")
    private String urlPerfix;

    private String getApiUrl(String method) {
        return urlPerfix + method;
    }

    private String getApiToken() {
        return DynamicTokenUtils.getTokenByTime(accApiToken);
    }

    private String mapToJsonStr(Map<String, Object> map) {
        if (map != null) {
            if (!map.containsKey(XjgjAccApiConstant.FIELD_KEY_STR)) {
                map.put(XjgjAccApiConstant.FIELD_KEY_STR, getApiToken());
            }
            return JSONUtils.beanToJson(map);
        } else {
            return "";
        }
    }

    /**
     * 查询会员绑定基本信息
     *
     * @param map 发送数据
     * @return 返回结果
     * @throws Exception
     */
    @Override
    public Map<String, Object> getMemberBindingInfo(Map<String, Object> map) throws Exception {
        String r = HttpUtils.postRequestSSL(getApiUrl(XjgjAccApiConstant.METHOD_GET_MEMBER_BINDING_INFO), mapToJsonStr(map));
        if (!r.isEmpty())
            return JSONUtils.jsonToMap(r);
        else
            return null;
    }

    /**
     * 会员密码验证
     *
     * @param map 发送数据
     * @return 返回结果
     * @throws Exception
     */
    @Override
    public Map<String, Object> checkMemberPassword(Map<String, Object> map) throws Exception {
        String r = HttpUtils.postRequestSSL(getApiUrl(XjgjAccApiConstant.METHOD_MEMBER_PASSWORD_CHECK), mapToJsonStr(map));
        if (!r.isEmpty())
            return JSONUtils.jsonToMap(r);
        else
            return null;
    }

    /**
     * 已注册会员绑定APP
     *
     * @param map 发送数据
     * @return 返回结果
     * @throws Exception
     */
    @Override
    public Map<String, Object> memberAppBind(Map<String, Object> map) throws Exception {
        String r = HttpUtils.postRequestSSL(getApiUrl(XjgjAccApiConstant.METHOD_MEMBER_APP_BIND), mapToJsonStr(map));
        if (!r.isEmpty())
            return JSONUtils.jsonToMap(r);
        else
            return null;
    }

    /**
     * 会员注册
     *
     * @param map 发送数据
     * @return 返回结果
     * @throws Exception
     */
    @Override
    public Map<String, Object> regMember(Map<String, Object> map) throws Exception {
        String r = HttpUtils.postRequestSSL(getApiUrl(XjgjAccApiConstant.METHOD_MEMBER_REG), mapToJsonStr(map));
        if (!r.isEmpty())
            return JSONUtils.jsonToMap(r);
        else
            return null;
    }

    /**
     * 会员圈存(充值)
     *
     * @param map 发送数据
     * @return 返回结果
     * @throws Exception
     */
    @Override
    public Map<String, Object> recharge(Map<String, Object> map) throws Exception {
        String r = HttpUtils.postRequestSSL(getApiUrl(XjgjAccApiConstant.METHOD_RECHARGE), mapToJsonStr(map));
        if (!r.isEmpty())
            return JSONUtils.jsonToMap(r);
        else
            return null;
    }

    /**
     * 会员圈存(充值)失败重试
     *
     * @param map 发送数据
     * @return 返回结果
     * @throws Exception
     */
    @Override
    public Map<String, Object> retryRecharge(Map<String, Object> map) throws Exception {
        String r = HttpUtils.postRequestSSL(getApiUrl(XjgjAccApiConstant.METHOD_RECHARGE_RETRY), mapToJsonStr(map));
        if (!r.isEmpty())
            return JSONUtils.jsonToMap(r);
        else
            return null;
    }

}
