package net.chenlin.dp.modules.base.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.chenlin.dp.common.annotation.SysLog;
import net.chenlin.dp.modules.sys.controller.AbstractController;
import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.modules.base.entity.MemberBankcardEntity;
import net.chenlin.dp.modules.base.service.MemberBankcardService;

/**
 * 会员绑定银行卡信息
 *
 * @author Andy
 * @email wangdy@mingze.com
 * @url www.mingze.com
 * @date 2017年12月12日 下午4:16:21
 */
@RestController
@RequestMapping("/base/member_bankcard")
public class MemberBankcardController extends AbstractController {
	
	@Autowired
	private MemberBankcardService memberBankcardService;
	
	/**
	 * 列表
	 * @param params
	 * @return
	 */
	@RequestMapping("/listData")
	public Page<MemberBankcardEntity> list(@RequestBody Map<String, Object> params) {
		return memberBankcardService.listMemberBankcard(params);
	}
		
	/**
	 * 新增
	 * @param memberBankcard
	 * @return
	 */
	@SysLog("新增会员绑定银行卡信息")
	@RequestMapping("/save")
	public R save(@RequestBody MemberBankcardEntity memberBankcard) {
		return memberBankcardService.saveMemberBankcard(memberBankcard);
	}
	
	/**
	 * 根据id查询详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/info")
	public R getById(@RequestBody Long id) {
		return memberBankcardService.getMemberBankcardById(id);
	}
	
	/**
	 * 修改
	 * @param memberBankcard
	 * @return
	 */
	@SysLog("修改会员绑定银行卡信息")
	@RequestMapping("/update")
	public R update(@RequestBody MemberBankcardEntity memberBankcard) {
		return memberBankcardService.updateMemberBankcard(memberBankcard);
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@SysLog("删除会员绑定银行卡信息")
	@RequestMapping("/remove")
	public R batchRemove(@RequestBody Long[] id) {
		return memberBankcardService.batchRemove(id);
	}
	
}
