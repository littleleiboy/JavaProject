package net.chenlin.dp.modules.api.service;

import java.util.Map;

public interface XjgjAccApiService {

    /**
     * 查询会员绑定基本信息
     *
     * @param map
     * @return
     * @throws Exception
     */
    Map<String, Object> getMemberBindingInfo(Map<String, Object> map) throws Exception;

    /**
     * 会员密码验证
     *
     * @param map
     * @return
     * @throws Exception
     */
    Map<String, Object> checkMemberPassword(Map<String, Object> map) throws Exception;

    /**
     * 已注册会员绑定APP
     *
     * @param map
     * @return
     * @throws Exception
     */
    Map<String, Object> memberAppBind(Map<String, Object> map) throws Exception;

    /**
     * 会员注册
     *
     * @param map
     * @return
     * @throws Exception
     */
    Map<String, Object> regMember(Map<String, Object> map) throws Exception;
}
