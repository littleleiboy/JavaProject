package net.chenlin.dp.modules.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.common.utils.CommonUtils;
import net.chenlin.dp.modules.base.entity.MemberInfoEntity;
import net.chenlin.dp.modules.base.manager.MemberInfoManager;
import net.chenlin.dp.modules.base.service.MemberInfoService;

/**
 * 会员信息
 *
 * @author Andy
 * @email wangdy@mingze.com
 * @url www.mingze.com
 * @date 2017年12月08日 上午10:57:51
 */
@Service("memberInfoService")
public class MemberInfoServiceImpl implements MemberInfoService {

    @Autowired
    private MemberInfoManager memberInfoManager;

    @Override
    public Page<MemberInfoEntity> listMemberInfo(Map<String, Object> params) {
        Query query = new Query(params);
        Page<MemberInfoEntity> page = new Page<>(query);
        memberInfoManager.listMemberInfo(page, query);
        return page;
    }

    @Override
    public MemberInfoEntity getMemberInfoByNO(String memberNO) {
        return memberInfoManager.getMemberInfoByNO(memberNO);
    }

    @Override
    public MemberInfoEntity getMemberInfoByMobile(String mobile) {
        return memberInfoManager.getMemberInfoByMobile(mobile);
    }

    @Override
    public R saveMemberInfo(MemberInfoEntity role) {
        int count = memberInfoManager.saveMemberInfo(role);
        return CommonUtils.msg(count);
    }

    @Override
    public R getMemberInfoById(Long id) {
        MemberInfoEntity memberInfo = memberInfoManager.getMemberInfoById(id);
        return CommonUtils.msg(memberInfo);
    }

    @Override
    public R updateMemberInfo(MemberInfoEntity memberInfo) {
        int count = memberInfoManager.updateMemberInfo(memberInfo);
        return CommonUtils.msg(count);
    }

    @Override
    public R batchRemove(Long[] id) {
        int count = memberInfoManager.batchRemove(id);
        return CommonUtils.msg(id, count);
    }

}
