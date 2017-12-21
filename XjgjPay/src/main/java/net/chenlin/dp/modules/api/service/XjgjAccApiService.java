package net.chenlin.dp.modules.api.service;

import java.util.Map;

public interface XjgjAccApiService {

    /**
     * 查询会员基本信息
     *
     * @param map
     * @return
     * @throws Exception
     */
    Map<String, Object> getMemberBaseInfo(Map<String, Object> map) throws Exception;

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

    /**
     * 会员圈提绑定
     *
     *
    * */
    Map<String, Object> memberBindBOC(Map<String, Object> map) throws Exception;

    /**
     * 会员圈提
     * @param map
     * @return
     * 2017-12-15
    * */
    Map<String,Object> memberWithDraw(Map<String, Object> map) throws Exception;

    /**
     * 会员账户余额查询
     * @param memberNo
     * @return
     * 2017-12-15
     * */
    Map<String,Object> searchMemberAccountBalance(String memberNo) throws Exception;

    /**
     * 一段时间内会员账户变动查询
     * @param map
     * @return
     * 2017-12-15
     * @throws Exception
    * */
    Map<String,Object> searchMemberCostLog(Map<String, Object> map) throws Exception;
}
