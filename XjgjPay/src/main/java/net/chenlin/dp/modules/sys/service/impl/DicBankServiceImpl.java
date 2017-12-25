package net.chenlin.dp.modules.sys.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.common.utils.CommonUtils;
import net.chenlin.dp.modules.sys.entity.DicBankEntity;
import net.chenlin.dp.modules.sys.manager.DicBankManager;
import net.chenlin.dp.modules.sys.service.DicBankService;

/**
 * 银行信息字典
 *
 * @author Andy
 * @email wangdy@mingze.com
 * @url www.mingze.com
 * @date 2017年12月25日 下午3:52:55
 */
@Service("dicBankService")
public class DicBankServiceImpl implements DicBankService {

	@Autowired
	private DicBankManager dicBankManager;

	@Override
	public Page<DicBankEntity> listDicBank(Map<String, Object> params) {
		Query query = new Query(params);
		Page<DicBankEntity> page = new Page<>(query);
		dicBankManager.listDicBank(page, query);
		return page;
	}

	@Override
	public R saveDicBank(DicBankEntity role) {
		int count = dicBankManager.saveDicBank(role);
		return CommonUtils.msg(count);
	}

	@Override
	public R getDicBankById(Long id) {
		DicBankEntity dicBank = dicBankManager.getDicBankById(id);
		return CommonUtils.msg(dicBank);
	}

	@Override
	public R updateDicBank(DicBankEntity dicBank) {
		int count = dicBankManager.updateDicBank(dicBank);
		return CommonUtils.msg(count);
	}

	@Override
	public R batchRemove(Long[] id) {
		int count = dicBankManager.batchRemove(id);
		return CommonUtils.msg(id, count);
	}

}
