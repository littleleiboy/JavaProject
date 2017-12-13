package net.chenlin.dp.modules.trade.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.chenlin.dp.common.annotation.SysLog;
import net.chenlin.dp.modules.sys.controller.AbstractController;
import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.modules.trade.entity.TradeLogEntity;
import net.chenlin.dp.modules.trade.service.TradeLogService;

/**
 * 交易记录
 *
 * @author Andy
 * @email wangdy@mingze.com
 * @url www.mingze.com
 * @date 2017年12月12日 下午4:59:03
 */
@RestController
@RequestMapping("/trade/trade_log")
public class TradeLogController extends AbstractController {
	
	@Autowired
	private TradeLogService tradeLogService;
	
	/**
	 * 列表
	 * @param params
	 * @return
	 */
	@RequestMapping("/listData")
	public Page<TradeLogEntity> list(@RequestBody Map<String, Object> params) {
		return tradeLogService.listTradeLog(params);
	}
		
	/**
	 * 新增
	 * @param tradeLog
	 * @return
	 */
	@SysLog("新增交易记录")
	@RequestMapping("/save")
	public R save(@RequestBody TradeLogEntity tradeLog) {
		return tradeLogService.saveTradeLog(tradeLog);
	}
	
	/**
	 * 根据id查询详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/info")
	public R getById(@RequestBody Long id) {
		return tradeLogService.getTradeLogById(id);
	}
	
	/**
	 * 修改
	 * @param tradeLog
	 * @return
	 */
	@SysLog("修改交易记录")
	@RequestMapping("/update")
	public R update(@RequestBody TradeLogEntity tradeLog) {
		return tradeLogService.updateTradeLog(tradeLog);
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@SysLog("删除交易记录")
	@RequestMapping("/remove")
	public R batchRemove(@RequestBody Long[] id) {
		return tradeLogService.batchRemove(id);
	}
	
}
