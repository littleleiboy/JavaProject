package net.chenlin.dp.modules.base.service;

import java.util.List;
import java.util.Map;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.modules.base.entity.MemberBankcardEntity;

/**
 * 会员绑定银行卡信息
 *
 * @author Andy
 * @email wangdy@mingze.com
 * @url www.mingze.com
 * @date 2017年12月12日 下午4:16:21
 */
public interface MemberBankcardService {

	Page<MemberBankcardEntity> listMemberBankcard(Map<String, Object> params);

	List<MemberBankcardEntity> listMemberBankcard(Map<String, Object> params, int defaultRowCount);

	R saveMemberBankcard(MemberBankcardEntity memberBankcard);
	
	R getMemberBankcardById(Long id);
	
	R updateMemberBankcard(MemberBankcardEntity memberBankcard);
	
	R batchRemove(Long[] id);

    MemberBankcardEntity getBankcardByBankCardID(String bankAccCard);

	MemberBankcardEntity getBankcardByBfBindID(String bfBindId);

	boolean removeBankcardInfoByBankcardNo(String bankAccCard);

	boolean updateMemberBankCardInfo(Long id);

	boolean updateWithdrawMemberBankCardInfo(Long id);
}
