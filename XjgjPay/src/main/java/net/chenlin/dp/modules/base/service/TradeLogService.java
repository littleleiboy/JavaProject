package net.chenlin.dp.modules.base.service;

import java.util.Map;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.modules.base.entity.TradeLogEntity;

/**
 * 交易记录
 *
 * @author Andy
 * @email wangdy@mingze.com
 * @url www.mingze.com
 * @date 2017年12月12日 下午4:59:03
 */
public interface TradeLogService {

	Page<TradeLogEntity> listTradeLog(Map<String, Object> params);
	
	R saveTradeLog(TradeLogEntity tradeLog);
	
	R getTradeLogById(Long id);
	
	R updateTradeLog(TradeLogEntity tradeLog);
	
	R batchRemove(Long[] id);
	
}
