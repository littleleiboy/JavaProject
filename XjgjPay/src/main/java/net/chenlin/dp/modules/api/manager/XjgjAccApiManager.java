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

    /**
     * 会员圈提
     * @param map 发送的数据
     * @return 返回值
     * */
    Map<String,Object> memberWithDraw(Map<String, Object> map) throws Exception;

    /**
     *查询会员账户余额
     * @param memberNo 发送数据
     * @return 返回结果
     * */
    Map<String,Object> searchMemberAccountBalance(String memberNo) throws Exception;

    /**
     * 查询一段时间内账户变动记录
     * @param map 参数
     * @return 返回结果
     * */
    Map<String,Object> searchMemberCostLog(Map<String, Object> map) throws Exception;

}
