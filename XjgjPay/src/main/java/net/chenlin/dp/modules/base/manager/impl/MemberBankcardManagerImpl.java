package net.chenlin.dp.modules.base.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.modules.base.dao.MemberBankcardMapper;
import net.chenlin.dp.modules.base.entity.MemberBankcardEntity;
import net.chenlin.dp.modules.base.manager.MemberBankcardManager;

/**
 * 会员绑定银行卡信息
 *
 * @author Andy
 * @email wangdy@mingze.com
 * @url www.mingze.com
 * @date 2017年12月12日 下午4:16:21
 */
@Component("memberBankcardManager")
public class MemberBankcardManagerImpl implements MemberBankcardManager {

    @Autowired
    private MemberBankcardMapper memberBankcardMapper;


    @Override
    public List<MemberBankcardEntity> listMemberBankcard(Page<MemberBankcardEntity> page, Query search) {
        return memberBankcardMapper.listForPage(page, search);
    }

    @Override
    public int saveMemberBankcard(MemberBankcardEntity memberBankcard) {
        return memberBankcardMapper.save(memberBankcard);
    }

    @Override
    public MemberBankcardEntity getMemberBankcardById(Long id) {
        MemberBankcardEntity memberBankcard = memberBankcardMapper.getObjectById(id);
        return memberBankcard;
    }

    @Override
    public int updateMemberBankcard(MemberBankcardEntity memberBankcard) {
        return memberBankcardMapper.update(memberBankcard);
    }

    @Override
    public int batchRemove(Long[] id) {
        int count = memberBankcardMapper.batchRemove(id);
        return count;
    }

    @Override
    public MemberBankcardEntity getBankcardByBankCardID(String bankAccCard) {
        return memberBankcardMapper.getBankcardByBankCardID(bankAccCard);
    }

    @Override
    public MemberBankcardEntity getBankcardByBfBindID(String bfBindId){
        return memberBankcardMapper.getBankcardByBfBindID(bfBindId);
    }

}
