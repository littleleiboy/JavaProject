package net.chenlin.dp.modules.base.service;

import java.util.Map;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.modules.base.entity.MemberInfoEntity;

/**
 * 会员信息
 *
 * @author Andy
 * @email wangdy@mingze.com
 * @url www.mingze.com
 * @date 2017年12月08日 上午10:57:51
 */
public interface MemberInfoService {

	Page<MemberInfoEntity> listMemberInfo(Map<String, Object> params);
	
	R saveMemberInfo(MemberInfoEntity memberInfo);
	
	R getMemberInfoById(Long id);
	
	R updateMemberInfo(MemberInfoEntity memberInfo);
	
	R batchRemove(Long[] id);
	
}
