package net.chenlin.dp.modules.sys.manager;

import java.util.List;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.modules.sys.entity.DicBankEntity;

/**
 * 银行信息字典
 *
 * @author Andy
 * @email wangdy@mingze.com
 * @url www.mingze.com
 * @date 2017年12月25日 下午3:52:55
 */
public interface DicBankManager {

	List<DicBankEntity> listDicBank(Page<DicBankEntity> page, Query search);

	List<DicBankEntity> listAll(Query search);

	int saveDicBank(DicBankEntity dicBank);
	
	DicBankEntity getDicBankById(Long id);
	
	int updateDicBank(DicBankEntity dicBank);
	
	int batchRemove(Long[] id);
	
}
