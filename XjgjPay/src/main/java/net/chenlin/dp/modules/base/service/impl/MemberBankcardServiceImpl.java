package net.chenlin.dp.modules.base.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.common.utils.CommonUtils;
import net.chenlin.dp.modules.base.entity.MemberBankcardEntity;
import net.chenlin.dp.modules.base.manager.MemberBankcardManager;
import net.chenlin.dp.modules.base.service.MemberBankcardService;

/**
 * 会员绑定银行卡信息
 *
 * @author Andy
 * @email wangdy@mingze.com
 * @url www.mingze.com
 * @date 2017年12月12日 下午4:16:21
 */
@Service("memberBankcardService")
public class MemberBankcardServiceImpl implements MemberBankcardService {

    @Autowired
    private MemberBankcardManager memberBankcardManager;

    @Override
    public Page<MemberBankcardEntity> listMemberBankcard(Map<String, Object> params) {
        Query query = new Query(params);
        Page<MemberBankcardEntity> page = new Page<>(query);
        memberBankcardManager.listMemberBankcard(page, query);
        return page;
    }

    @Override
    public R saveMemberBankcard(MemberBankcardEntity role) {
        int count = memberBankcardManager.saveMemberBankcard(role);
        return CommonUtils.msg(count);
    }

    @Override
    public R getMemberBankcardById(Long id) {
        MemberBankcardEntity memberBankcard = memberBankcardManager.getMemberBankcardById(id);
        return CommonUtils.msg(memberBankcard);
    }

    @Override
    public R updateMemberBankcard(MemberBankcardEntity memberBankcard) {
        int count = memberBankcardManager.updateMemberBankcard(memberBankcard);
        return CommonUtils.msg(count);
    }

    @Override
    public R batchRemove(Long[] id) {
        int count = memberBankcardManager.batchRemove(id);
        return CommonUtils.msg(id, count);
    }

    @Override
    public MemberBankcardEntity getBankcardByBankCardID(String bankAccCard) {
        return memberBankcardManager.getBankcardByBankCardID(bankAccCard);
    }

    @Override
    public MemberBankcardEntity getBankcardByBfBindID(String bfBindId) {
        return memberBankcardManager.getBankcardByBfBindID(bfBindId);
    }

}
