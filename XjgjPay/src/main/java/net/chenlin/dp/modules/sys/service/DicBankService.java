package net.chenlin.dp.modules.sys.service;

import java.util.List;
import java.util.Map;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.modules.sys.entity.DicBankEntity;

/**
 * 银行信息字典
 *
 * @author Andy
 * @email wangdy@mingze.com
 * @url www.mingze.com
 * @date 2017年12月25日 下午3:52:55
 */
public interface DicBankService {

	Page<DicBankEntity> listDicBank(Map<String, Object> params);

	List<DicBankEntity> listAll(Map<String, Object> params);

	R saveDicBank(DicBankEntity dicBank);
	
	R getDicBankById(Long id);
	
	R updateDicBank(DicBankEntity dicBank);
	
	R batchRemove(Long[] id);
	
}
