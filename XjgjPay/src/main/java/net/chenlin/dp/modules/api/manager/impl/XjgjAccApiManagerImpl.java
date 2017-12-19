package net.chenlin.dp.modules.api.manager.impl;

import net.chenlin.dp.common.constant.XjgjAccApiConstant;
import net.chenlin.dp.common.utils.DynamicTokenUtils;
import net.chenlin.dp.common.utils.HttpUtils;
import net.chenlin.dp.common.utils.JacksonUtils;
import net.chenlin.dp.modules.api.manager.XjgjAccApiManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 西郊国际结算系统API管理
 */
@Component("xjgjAccApiManager")
public class XjgjAccApiManagerImpl implements XjgjAccApiManager {

    @Value("${myprop.api.jsxt.app-key}")
    private String accApiToken;

    @Value("${myprop.api.jsxt.app-url}")
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
            return JacksonUtils.beanToJson(map);
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
            return JacksonUtils.jsonToMap(r);
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
            return JacksonUtils.jsonToMap(r);
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
            return JacksonUtils.jsonToMap(r);
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
            return JacksonUtils.jsonToMap(r);
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
            return JacksonUtils.jsonToMap(r);
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
            return JacksonUtils.jsonToMap(r);
        else
            return null;
    }

    /**
     * 会员圈提
     * @param map 发送的数据
     * @return 返回值
     * */
    public Map<String,Object> memberWithDraw(Map<String, Object> map) throws Exception{
        return JacksonUtils.jsonToMap(HttpUtils.postRequestSSL(getApiUrl(XjgjAccApiConstant.METHOD_MEMBER_WITH_DRAW), mapToJsonStr(map)));
    }

    /**
     *查询会员账户余额
     * @param memberNo 发送数据
     * @return 返回结果
     * */
    public Map<String,Object> searchMemberAccountBalance(String memberNo) throws Exception{
        return JacksonUtils.jsonToMap(HttpUtils.postRequestSSL(getApiUrl(XjgjAccApiConstant.SEARCH_MEMBER_ACCOUNT_BALANCE), memberNo));
    }

    /**
     * 查询一段时间内账户变动记录
     * @param map 参数
     * @return 返回结果
     * */
    public Map<String,Object> searchMemberCostLog(Map<String, Object> map) throws Exception{
        return JacksonUtils.jsonToMap(HttpUtils.postRequestSSL(getApiUrl(XjgjAccApiConstant.SEARCH_MEMBER_COST_LOG), mapToJsonStr(map)));
    }


}
