package net.chenlin.dp.modules.trade.manager;

import java.util.List;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.modules.trade.entity.TradeLogEntity;

/**
 * 交易记录
 *
 * @author Andy
 * @email wangdy@mingze.com
 * @url www.mingze.com
 * @date 2017年12月12日 下午4:59:03
 */
public interface TradeLogManager {

	List<TradeLogEntity> listTradeLog(Page<TradeLogEntity> page, Query search);
	
	int saveTradeLog(TradeLogEntity tradeLog);
	
	TradeLogEntity getTradeLogById(Long id);
	
	int updateTradeLog(TradeLogEntity tradeLog);
	
	int batchRemove(Long[] id);
	
}
