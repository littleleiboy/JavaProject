package net.chenlin.dp.modules.base.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.modules.base.dao.TradeLogMapper;
import net.chenlin.dp.modules.base.entity.TradeLogEntity;
import net.chenlin.dp.modules.base.manager.TradeLogManager;

/**
 * 交易记录
 *
 * @author Andy
 * @email wangdy@mingze.com
 * @url www.mingze.com
 * @date 2017年12月12日 下午4:59:03
 */
@Component("tradeLogManager")
public class TradeLogManagerImpl implements TradeLogManager {

	@Autowired
	private TradeLogMapper tradeLogMapper;
	

	@Override
	public List<TradeLogEntity> listTradeLog(Page<TradeLogEntity> page, Query search) {
		return tradeLogMapper.listForPage(page, search);
	}

	@Override
	public int saveTradeLog(TradeLogEntity tradeLog) {
		return tradeLogMapper.save(tradeLog);
	}

	@Override
	public TradeLogEntity getTradeLogById(Long id) {
		TradeLogEntity tradeLog = tradeLogMapper.getObjectById(id);
		return tradeLog;
	}

	@Override
	public int updateTradeLog(TradeLogEntity tradeLog) {
		return tradeLogMapper.update(tradeLog);
	}

	@Override
	public int batchRemove(Long[] id) {
		int count = tradeLogMapper.batchRemove(id);
		return count;
	}
	
}
