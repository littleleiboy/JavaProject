package net.chenlin.dp.modules.base.dao;

import org.apache.ibatis.annotations.Mapper;

import net.chenlin.dp.modules.base.entity.MemberBankcardEntity;
import net.chenlin.dp.modules.sys.dao.BaseMapper;

/**
 * 会员绑定银行卡信息
 *
 * @author Andy
 * @email wangdy@mingze.com
 * @url www.mingze.com
 * @date 2017年12月12日 下午4:16:21
 */
@Mapper
public interface MemberBankcardMapper extends BaseMapper<MemberBankcardEntity> {

    MemberBankcardEntity getBankcardByBankCardID(String bankAccCard);

    MemberBankcardEntity getBankcardByBfBindID(String bfBindId);

    boolean removeBankcardInfoByBankcardNo(String bankAccCard);

    boolean updateMemberBankInfo(long id);

    boolean updateWithdrawMemberBankCardInfo(Long id);
}
