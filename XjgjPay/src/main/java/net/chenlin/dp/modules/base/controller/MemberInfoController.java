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
import net.chenlin.dp.modules.base.entity.MemberInfoEntity;
import net.chenlin.dp.modules.base.service.MemberInfoService;

/**
 * 会员信息
 *
 * @author Andy
 * @email wangdy@mingze.com
 * @url www.mingze.com
 * @date 2017年12月08日 上午10:57:51
 */
@RestController
@RequestMapping("/base/member")
public class MemberInfoController extends AbstractController {

    @Autowired
    private MemberInfoService memberInfoService;

    /**
     * 列表
     *
     * @param params
     * @return
     */
    @RequestMapping("/listData")
    public Page<MemberInfoEntity> list(@RequestBody(required = false) Map<String, Object> params) {
        return memberInfoService.listMemberInfo(params);
    }

    /**
     * 新增
     *
     * @param memberInfo
     * @return
     */
    @SysLog("新增会员信息")
    @RequestMapping("/save")
    public R save(@RequestBody MemberInfoEntity memberInfo) {
        return memberInfoService.saveMemberInfo(memberInfo);
    }

    /**
     * 根据id查询详情
     *
     * @param id
     * @return
     */
    @RequestMapping("/info")
    public R getById(@RequestBody Long id) {
        return memberInfoService.getMemberInfoById(id);
    }

    /**
     * 修改
     *
     * @param memberInfo
     * @return
     */
    @SysLog("修改会员信息")
    @RequestMapping("/update")
    public R update(@RequestBody MemberInfoEntity memberInfo) {
        return memberInfoService.updateMemberInfo(memberInfo);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @SysLog("删除会员信息")
    @RequestMapping("/remove")
    public R batchRemove(@RequestBody Long[] id) {
        return memberInfoService.batchRemove(id);
    }

}
