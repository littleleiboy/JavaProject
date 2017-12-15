package net.chenlin.dp.modules.api.controller;

import net.chenlin.dp.common.constant.MsgConstant;
import net.chenlin.dp.common.entity.ResultData;
import net.chenlin.dp.modules.api.service.SmsApiService;
import net.chenlin.dp.modules.api.service.XjgjAccApiService;
import net.chenlin.dp.modules.sys.controller.AbstractController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/xjgjacc")
public class XjgjAccApiController extends AbstractController {

    private final static Logger logger = LoggerFactory.getLogger(XjgjAccApiController.class);

    @Autowired
    private XjgjAccApiService xjgjService;

    /**
     * 查询会员绑定基本信息
     *
     * @param params
     * @return
     */
    @RequestMapping("/getMemberBindingInfo")
    public ResultData getMemberBindingInfo(@RequestBody Map<String, Object> params) {
        try {
            Map<String, Object> mapResult = xjgjService.getMemberBindingInfo(params);
            if (mapResult != null) {
                return new ResultData("r1", true, MsgConstant.MSG_OPERATION_SUCCESS, mapResult);
            } else {
                return new ResultData("e2", false, MsgConstant.MSG_REMOTE_ERROR, "");
            }
        } catch (Exception e) {
            logger.error(MsgConstant.MSG_OPERATION_FAILED, e);
            return new ResultData("e1", false, MsgConstant.MSG_OPERATION_FAILED + e.getMessage(), "");
        }
    }

    /**
     * 验证会员密码
     *
     * @param params
     * @return
     */
    @RequestMapping("/checkMemberPass")
    public ResultData checkMemberPassword(@RequestBody Map<String, Object> params) {
        try {
            Map<String, Object> mapResult = xjgjService.checkMemberPassword(params);
            if (mapResult != null) {
                return new ResultData("r1", true, MsgConstant.MSG_OPERATION_SUCCESS, mapResult);
            } else {
                return new ResultData("e2", false, MsgConstant.MSG_REMOTE_ERROR, "");
            }
        } catch (Exception e) {
            logger.error(MsgConstant.MSG_OPERATION_FAILED, e);
            return new ResultData("e1", false, MsgConstant.MSG_OPERATION_FAILED + e.getMessage(), "");
        }
    }

    /**
     * 已注册会员绑定
     *
     * @param params
     * @return
     */
    @RequestMapping("/memberAppBind")
    public ResultData memberAppBind(@RequestBody Map<String, Object> params) {
        try {
            Map<String, Object> mapResult = xjgjService.memberAppBind(params);
            if (mapResult != null) {
                return new ResultData("r1", true, MsgConstant.MSG_OPERATION_SUCCESS, mapResult);
            } else {
                return new ResultData("e2", false, MsgConstant.MSG_REMOTE_ERROR, "");
            }
        } catch (Exception e) {
            logger.error(MsgConstant.MSG_OPERATION_FAILED, e);
            return new ResultData("e1", false, MsgConstant.MSG_OPERATION_FAILED + e.getMessage(), "");
        }
    }

    /**
     * 会员注册
     *
     * @param params
     * @return
     */
    @RequestMapping("/regMember")
    public ResultData regMember(@RequestBody Map<String, Object> params) {
        try {
            Map<String, Object> mapResult = xjgjService.regMember(params);
            if (mapResult != null) {
                return new ResultData("r1", true, MsgConstant.MSG_OPERATION_SUCCESS, mapResult);
            } else {
                return new ResultData("e2", false, MsgConstant.MSG_REMOTE_ERROR, "");
            }
        } catch (Exception e) {
            logger.error(MsgConstant.MSG_OPERATION_FAILED, e);
            return new ResultData("e1", false, MsgConstant.MSG_OPERATION_FAILED + e.getMessage(), "");
        }
    }

}
