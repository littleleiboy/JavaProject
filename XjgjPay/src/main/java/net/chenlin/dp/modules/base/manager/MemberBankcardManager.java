package net.chenlin.dp.modules.base.manager;

import java.util.List;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.modules.base.entity.MemberBankcardEntity;

/**
 * 会员绑定银行卡信息
 *
 * @author Andy
 * @email wangdy@mingze.com
 * @url www.mingze.com
 * @date 2017年12月12日 下午4:16:21
 */
public interface MemberBankcardManager {

	List<MemberBankcardEntity> listMemberBankcard(Page<MemberBankcardEntity> page, Query search);
	
	int saveMemberBankcard(MemberBankcardEntity memberBankcard);
	
	MemberBankcardEntity getMemberBankcardById(Long id);
	
	int updateMemberBankcard(MemberBankcardEntity memberBankcard);
	
	int batchRemove(Long[] id);

	MemberBankcardEntity getBankcardByBankCardID(String bankAccCard);

	MemberBankcardEntity getBankcardByBfBindID(String bfBindId);

	boolean removeBankcardInfoByBankcardNo(String bankAccCard);
	boolean updateMemberBankInfo(long id);
}
