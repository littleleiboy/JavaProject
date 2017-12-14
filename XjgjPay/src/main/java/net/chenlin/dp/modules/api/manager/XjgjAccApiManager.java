package net.chenlin.dp.modules.api.manager;

import java.util.Map;

/**
 * 西郊国际结算系统API管理
 */
public interface XjgjAccApiManager {

    /**
     * 查询会员绑定基本信息
     *
     * @param map 发送数据
     * @return 返回结果
     * @throws Exception
     */
    Map<String, Object> getMemberBindingInfo(Map<String, Object> map) throws Exception;

    /**
     * 会员密码验证
     *
     * @param map 发送数据
     * @return 返回结果
     * @throws Exception
     */
    Map<String, Object> checkMemberPassword(Map<String, Object> map) throws Exception;

    /**
     * 已注册会员绑定APP
     *
     * @param map 发送数据
     * @return 返回结果
     * @throws Exception
     */
    Map<String, Object> memberAppBind(Map<String, Object> map) throws Exception;

    /**
     * 会员注册
     *
     * @param map 发送数据
     * @return 返回结果
     * @throws Exception
     */
    Map<String, Object> regMember(Map<String, Object> map) throws Exception;

    /**
     * 会员圈存(充值)
     *
     * @param map 发送数据
     * @return 返回结果
     * @throws Exception
     */
    Map<String, Object> recharge(Map<String, Object> map) throws Exception;

    /**
     * 会员圈存(充值)失败重试
     *
     * @param map 发送数据
     * @return 返回结果
     * @throws Exception
     */
    Map<String, Object> retryRecharge(Map<String, Object> map) throws Exception;
}
