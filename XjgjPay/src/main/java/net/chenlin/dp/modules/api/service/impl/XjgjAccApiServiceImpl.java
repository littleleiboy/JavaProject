package net.chenlin.dp.modules.api.service.impl;

import net.chenlin.dp.modules.api.manager.XjgjAccApiManager;
import net.chenlin.dp.modules.api.service.XjgjAccApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service("xjgjAccApiService")
public class XjgjAccApiServiceImpl implements XjgjAccApiService {

    @Autowired
    private XjgjAccApiManager apiManager;

    /**
     * 查询会员基本信息
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> getMemberBaseInfo(Map<String, Object> map) throws Exception {
        return apiManager.getMemberBaseInfo(map);
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

    /**
     * 会员圈存（充值）
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> recharge(Map<String, Object> map) throws Exception {
        return apiManager.recharge(map);
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
        return apiManager.retryRecharge(map);
    }


    /**
     * 会员圈提绑定
     *
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> memberBindBOC(Map<String, Object> map) throws Exception {
        return apiManager.memberBindBOC(map);
    }

    /**
     * 会员圈提解绑
     *
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> memberUnBindBOC(Map<String, Object> map) throws Exception {
        return apiManager.memberUnBindBOC(map);
    }

    /**
     * 会员圈提
     * 2017-12-15
     *
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> memberWithDraw(Map<String, Object> map) throws Exception {
        return apiManager.memberWithDraw(map);
    }

    /**
     * 会员账户余额查询
     * 2017-12-15
     *
     * @param memberNo
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> searchMemberAccountBalance(Map<String, Object> memberNo) throws Exception {
        return apiManager.searchMemberAccountBalance(memberNo);
    }

    /**
     * 一段时间内账户变动查询
     * 2017-12-15
     *
     * @param
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> searchMemberCostLog(Map<String, Object> map) throws Exception {
        return apiManager.searchMemberCostLog(map);
    }

    @Override
    public BigDecimal poundageRecharge(BigDecimal amount, String bankId) {
        //圈存手续费产生规则：
        //中国银行账户免手续费，其他银行账户按0.18%的费率产生手续费，且手续费最低2元，最高18元。
        BigDecimal poundage = new BigDecimal("0");
        BigDecimal rate = new BigDecimal("0.0018");
        BigDecimal minPoundage = new BigDecimal("2");
        BigDecimal maxPoundage = new BigDecimal("18");
        if (!"BOC".equalsIgnoreCase(bankId)) {//非中国银行账户收手续费
            poundage = amount.multiply(rate);
            if (poundage.compareTo(minPoundage) == -1) {//小于最低手续费取最低
                poundage = minPoundage;
            } else if (poundage.compareTo(maxPoundage) == 1) {//大于最高手续费取最高
                poundage = maxPoundage;
            }
        }
        return poundage;
    }
}
