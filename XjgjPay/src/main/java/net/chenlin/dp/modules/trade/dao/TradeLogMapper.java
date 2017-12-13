package net.chenlin.dp.modules.trade.dao;

import org.apache.ibatis.annotations.Mapper;

import net.chenlin.dp.modules.trade.entity.TradeLogEntity;
import net.chenlin.dp.modules.sys.dao.BaseMapper;

/**
 * 交易记录
 *
 * @author Andy
 * @email wangdy@mingze.com
 * @url www.mingze.com
 * @date 2017年12月12日 下午4:59:03
 */
@Mapper
public interface TradeLogMapper extends BaseMapper<TradeLogEntity> {
	
}
