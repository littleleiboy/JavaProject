package net.chenlin.dp.modules.trade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.common.utils.CommonUtils;
import net.chenlin.dp.modules.trade.entity.TradeLogEntity;
import net.chenlin.dp.modules.trade.manager.TradeLogManager;
import net.chenlin.dp.modules.trade.service.TradeLogService;

/**
 * 交易记录
 *
 * @author Andy
 * @email wangdy@mingze.com
 * @url www.mingze.com
 * @date 2017年12月12日 下午4:59:03
 */
@Service("tradeLogService")
public class TradeLogServiceImpl implements TradeLogService {

	@Autowired
	private TradeLogManager tradeLogManager;

	@Override
	public Page<TradeLogEntity> listTradeLog(Map<String, Object> params) {
		Query query = new Query(params);
		Page<TradeLogEntity> page = new Page<>(query);
		tradeLogManager.listTradeLog(page, query);
		return page;
	}

	@Override
	public R saveTradeLog(TradeLogEntity role) {
		int count = tradeLogManager.saveTradeLog(role);
		return CommonUtils.msg(count);
	}

	@Override
	public R getTradeLogById(Long id) {
		TradeLogEntity tradeLog = tradeLogManager.getTradeLogById(id);
		return CommonUtils.msg(tradeLog);
	}

	@Override
	public R updateTradeLog(TradeLogEntity tradeLog) {
		int count = tradeLogManager.updateTradeLog(tradeLog);
		return CommonUtils.msg(count);
	}

	@Override
	public R batchRemove(Long[] id) {
		int count = tradeLogManager.batchRemove(id);
		return CommonUtils.msg(id, count);
	}

}
