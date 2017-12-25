package net.chenlin.dp.modules.sys.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.chenlin.dp.common.annotation.SysLog;
import net.chenlin.dp.modules.sys.controller.AbstractController;
import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.modules.sys.entity.DicBankEntity;
import net.chenlin.dp.modules.sys.service.DicBankService;

/**
 * 银行信息字典
 *
 * @author Andy
 * @email wangdy@mingze.com
 * @url www.mingze.com
 * @date 2017年12月25日 下午3:52:55
 */
@RestController
@RequestMapping("/sys/bank")
public class DicBankController extends AbstractController {
	
	@Autowired
	private DicBankService dicBankService;
	
	/**
	 * 列表
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
	public Page<DicBankEntity> list(@RequestBody Map<String, Object> params) {
		return dicBankService.listDicBank(params);
	}
		
	/**
	 * 新增
	 * @param dicBank
	 * @return
	 */
	@SysLog("新增银行信息字典")
	@RequestMapping("/save")
	public R save(@RequestBody DicBankEntity dicBank) {
		return dicBankService.saveDicBank(dicBank);
	}
	
	/**
	 * 根据id查询详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/info")
	public R getById(@RequestBody Long id) {
		return dicBankService.getDicBankById(id);
	}
	
	/**
	 * 修改
	 * @param dicBank
	 * @return
	 */
	@SysLog("修改银行信息字典")
	@RequestMapping("/update")
	public R update(@RequestBody DicBankEntity dicBank) {
		return dicBankService.updateDicBank(dicBank);
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@SysLog("删除银行信息字典")
	@RequestMapping("/remove")
	public R batchRemove(@RequestBody Long[] id) {
		return dicBankService.batchRemove(id);
	}
	
}
