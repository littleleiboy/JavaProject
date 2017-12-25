package net.chenlin.dp.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import net.chenlin.dp.modules.sys.entity.DicBankEntity;
import net.chenlin.dp.modules.sys.dao.BaseMapper;

/**
 * 银行信息字典
 *
 * @author Andy
 * @email wangdy@mingze.com
 * @url www.mingze.com
 * @date 2017年12月25日 下午3:52:55
 */
@Mapper
public interface DicBankMapper extends BaseMapper<DicBankEntity> {
	
}
