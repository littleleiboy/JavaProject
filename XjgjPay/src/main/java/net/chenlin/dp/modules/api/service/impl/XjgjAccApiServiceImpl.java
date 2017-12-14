package net.chenlin.dp.modules.api.service.impl;

import net.chenlin.dp.modules.api.manager.XjgjAccApiManager;
import net.chenlin.dp.modules.api.service.XjgjAccApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("xjgjAccApiService")
public class XjgjAccApiServiceImpl implements XjgjAccApiService {

    @Autowired
    private XjgjAccApiManager apiManager;

    /**
     * 查询会员绑定基本信息
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> getMemberBindingInfo(Map<String, Object> map) throws Exception {
        return apiManager.getMemberBindingInfo(map);
    }

    /**
     * 会员密码验证
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> checkMemberPassword(Map<String, Object> map) throws Exception {
        return apiManager.checkMemberPassword(map);
    }

    /**
     * 已注册会员绑定APP
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> memberAppBind(Map<String, Object> map) throws Exception {
        return apiManager.memberAppBind(map);
    }

    /**
     * 会员注册
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> regMember(Map<String, Object> map) throws Exception {
        return apiManager.regMember(map);
    }

}
