package net.chenlin.dp.modules.base.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.modules.base.dao.MemberInfoMapper;
import net.chenlin.dp.modules.base.entity.MemberInfoEntity;
import net.chenlin.dp.modules.base.manager.MemberInfoManager;

/**
 * 会员信息
 *
 * @author Andy
 * @email wangdy@mingze.com
 * @url www.mingze.com
 * @date 2017年12月08日 上午10:57:51
 */
@Component("memberInfoManager")
public class MemberInfoManagerImpl implements MemberInfoManager {

	@Autowired
	private MemberInfoMapper memberInfoMapper;
	

	@Override
	public List<MemberInfoEntity> listMemberInfo(Page<MemberInfoEntity> page, Query search) {
		return memberInfoMapper.listForPage(page, search);
	}

	@Override
	public int saveMemberInfo(MemberInfoEntity memberInfo) {
		return memberInfoMapper.save(memberInfo);
	}

	@Override
	public MemberInfoEntity getMemberInfoById(Long id) {
		MemberInfoEntity memberInfo = memberInfoMapper.getObjectById(id);
		return memberInfo;
	}

	@Override
	public int updateMemberInfo(MemberInfoEntity memberInfo) {
		return memberInfoMapper.update(memberInfo);
	}

	@Override
	public int batchRemove(Long[] id) {
		int count = memberInfoMapper.batchRemove(id);
		return count;
	}
	
}
