package net.chenlin.dp.modules.api.controller;

import net.chenlin.dp.common.constant.MsgConstant;
import net.chenlin.dp.common.constant.XjgjAccApiConstant;
import net.chenlin.dp.common.entity.ResultData;
import net.chenlin.dp.common.utils.JacksonUtils;
import net.chenlin.dp.modules.api.service.XjgjAccApiService;
import net.chenlin.dp.modules.base.entity.MemberInfoEntity;
import net.chenlin.dp.modules.base.service.MemberBankcardService;
import net.chenlin.dp.modules.base.service.MemberInfoService;
import net.chenlin.dp.modules.sys.controller.AbstractController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/app")
public class AppController extends AbstractController {

    private final static Logger logger = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private XjgjAccApiService apiService;

    @Autowired
    private MemberInfoService memberInfoService;

    @Autowired
    private MemberBankcardService memberBankService;

    /**
     * 查询会员基本信息
     *
     * @param params
     * @return
     */
    @RequestMapping("/getMemberBaseInfo")
    public ResultData getMemberBaseInfo(@RequestBody Map<String, Object> params) {
        try {
            Map<String, Object> mapResult = apiService.getMemberBaseInfo(params);
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
            Map<String, Object> mapResult = apiService.checkMemberPassword(params);
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
     * 会员绑定
     *
     * @param params
     * @return
     */
    @RequestMapping("/memberAppBind")
    public ResultData memberAppBind(@RequestBody Map<String, Object> params) {
        try {
            if (params != null) {
                //必要的后台验证
                Object memberNO = params.get(XjgjAccApiConstant.FIELD_MEMBER_NO);
                if (null == memberNO || "".equals(memberNO)) {
                    return new ResultData("err_memberNO_isnull", false, "会员账号不能为空！");
                }
                Object memberName = params.get(XjgjAccApiConstant.FIELD_MEMBER_NAME);
                if (null == memberName || "".equals(memberName)) {
                    return new ResultData("err_memberName_isnull", false, "会员姓名不能为空！");
                }
                Object idCard = params.get(XjgjAccApiConstant.FIELD_ID_CARD);
                if (null == idCard || "".equals(idCard)) {
                    return new ResultData("err_idCard_isnull", false, "身份证号不能为空！");
                }
                Object mobileNum = params.get(XjgjAccApiConstant.FIELD_MOBILE_NUM);
                if (null == mobileNum || "".equals(mobileNum)) {
                    return new ResultData("err_mobileNum_isnull", false, "银行预留手机号不能为空！");
                }
                Object pass = params.get(XjgjAccApiConstant.FIELD_PASSWORD);
                if (null == pass || "".equals(pass)) {
                    return new ResultData("err_password_isnull", false, "会员密码不能为空！");
                }

                //TODO 保存会员注册填写的信息到数据库
                MemberInfoEntity memberEntity = memberInfoService.getMemberInfoByNO(String.valueOf(memberNO));

                //调接口推送会员注册或绑定信息给结算系统
                Map<String, Object> mapResult = apiService.memberAppBind(params);
                if (mapResult != null) {
                    Object resultState = mapResult.get(XjgjAccApiConstant.FIELD_RESULT);
                    Object message = mapResult.get(XjgjAccApiConstant.FIELD_MESSAGE);
                    Object isQtBinded = mapResult.get(XjgjAccApiConstant.FIELD_IS_QT_BINDED);
                    Object bankAccount = mapResult.get(XjgjAccApiConstant.FIELD_BANK_ACCOUNT);
                    Object mCardNO = mapResult.get(XjgjAccApiConstant.FIELD_MEMBER_CARD_NO);
                    if ("1".equals(resultState)) {//调用结算系统会员绑定接口处理成功
                        //TODO 调接口绑定宝付

                        return new ResultData("ok", true, MsgConstant.MSG_OPERATION_SUCCESS, mapResult);
                    } else {
                        logger.error(String.valueOf(message));
                        return new ResultData("err_remote_response", false, String.valueOf(message), mapResult);
                    }

                } else {
                    logger.error(MsgConstant.MSG_REMOTE_ERROR);
                    return new ResultData("err_remote_err", false, MsgConstant.MSG_REMOTE_ERROR);
                }
            } else {
                return new ResultData("err_param_isnull", false, "请求参数不能为空！");
            }
        } catch (Exception e) {
            logger.error(MsgConstant.MSG_OPERATION_FAILED, e);
            return new ResultData("err_exception", false, MsgConstant.MSG_OPERATION_FAILED + e.getMessage());
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
            Map<String, Object> mapResult = apiService.regMember(params);
            if (mapResult != null) {
                //TODO 保存会员信息
                return new ResultData("r1", true, MsgConstant.MSG_OPERATION_SUCCESS, mapResult);
            } else {
                return new ResultData("e2", false, MsgConstant.MSG_REMOTE_ERROR, "");
            }
        } catch (Exception e) {
            logger.error(MsgConstant.MSG_OPERATION_FAILED, e);
            return new ResultData("e1", false, MsgConstant.MSG_OPERATION_FAILED + e.getMessage(), "");
        }
    }


    @RequestMapping("/memberWithDraw")
    public ResultData memberWithDraw(@RequestBody Map<String, Object> params) {
        Object result = null;
        String accountBalancekey = "accountQty";
        String drawKey = "moneyQty";
        try {
            Map<String, Object> map = apiService.memberWithDraw(params);
            Map<String, Object> momenyBalanceMap = apiService.searchMemberAccountBalance(params.get("memberNo").toString());
            result = JacksonUtils.mapToBean(map, MemberInfoEntity.class);
            int accountBalance = (int) momenyBalanceMap.get(accountBalancekey);
            int drawMoney = (int) params.get(drawKey);
            if (accountBalance <= 0 && drawMoney > accountBalance) {
                return new ResultData("err", false, "账户余额不足！", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultData("ok", true, "查询成功", result);
    }

    @RequestMapping("/searchMemberAccountBalance")
    public ResultData searchMemberAccountBalance(@RequestBody String param) {
        Object result = null;
        try {
            Map<String, Object> mapResult = apiService.searchMemberAccountBalance(param);
            result = JacksonUtils.mapToBean(mapResult, MemberInfoEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultData("ok", true, "查询成功", result);
    }

    @RequestMapping("/searchMemberAccountChange")
    public ResultData searchMemberAccountChangeByPeriodOfTime(Map<String, Object> map) {
        Object result = null;
        try {
            Map<String, Object> mapResult = apiService.searchMemberCostLog(map);
            result = JacksonUtils.mapToBean(mapResult, MemberInfoEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultData("ok", true, "查询成功", result);
    }

}
