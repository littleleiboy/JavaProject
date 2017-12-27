package net.chenlin.dp.modules.sys.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.modules.sys.dao.DicBankMapper;
import net.chenlin.dp.modules.sys.entity.DicBankEntity;
import net.chenlin.dp.modules.sys.manager.DicBankManager;

/**
 * 银行信息字典
 *
 * @author Andy
 * @email wangdy@mingze.com
 * @url www.mingze.com
 * @date 2017年12月25日 下午3:52:55
 */
@Component("dicBankManager")
public class DicBankManagerImpl implements DicBankManager {

	@Autowired
	private DicBankMapper dicBankMapper;
	

	@Override
	public List<DicBankEntity> listDicBank(Page<DicBankEntity> page, Query search) {
		return dicBankMapper.listForPage(page, search);
	}

	@Override
	public List<DicBankEntity> listAll(Query search) {
		return dicBankMapper.list(search);
	}

	@Override
	public int saveDicBank(DicBankEntity dicBank) {
		return dicBankMapper.save(dicBank);
	}

	@Override
	public DicBankEntity getDicBankById(Long id) {
		DicBankEntity dicBank = dicBankMapper.getObjectById(id);
		return dicBank;
	}

	@Override
	public int updateDicBank(DicBankEntity dicBank) {
		return dicBankMapper.update(dicBank);
	}

	@Override
	public int batchRemove(Long[] id) {
		int count = dicBankMapper.batchRemove(id);
		return count;
	}
	
}
