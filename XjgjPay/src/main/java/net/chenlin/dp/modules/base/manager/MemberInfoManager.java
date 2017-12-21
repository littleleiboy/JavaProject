package net.chenlin.dp.modules.base.manager;

import java.util.List;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.modules.base.entity.MemberInfoEntity;

/**
 * 会员信息
 *
 * @author Andy
 * @email wangdy@mingze.com
 * @url www.mingze.com
 * @date 2017年12月08日 上午10:57:51
 */
public interface MemberInfoManager {

	List<MemberInfoEntity> listMemberInfo(Page<MemberInfoEntity> page, Query search);
	
	int saveMemberInfo(MemberInfoEntity memberInfo);
	
	MemberInfoEntity getMemberInfoById(Long id);
	
	int updateMemberInfo(MemberInfoEntity memberInfo);
	
	int batchRemove(Long[] id);

	MemberInfoEntity getMemberInfoByNO(String memberNO);

	MemberInfoEntity getMemberInfoByMobile(String mobile);
}
