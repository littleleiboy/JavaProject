package net.chenlin.dp.modules.base.dao;

import org.apache.ibatis.annotations.Mapper;

import net.chenlin.dp.modules.base.entity.MemberInfoEntity;
import net.chenlin.dp.modules.sys.dao.BaseMapper;

/**
 * 会员信息
 *
 * @author Andy
 * @email wangdy@mingze.com
 * @url www.mingze.com
 * @date 2017年12月08日 上午10:57:51
 */
@Mapper
public interface MemberInfoMapper extends BaseMapper<MemberInfoEntity> {

    MemberInfoEntity getMemberInfoByNO(String memberNO);

    MemberInfoEntity getMemberInfoByMobile(String mobile);

}
