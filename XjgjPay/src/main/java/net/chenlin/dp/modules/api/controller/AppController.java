package net.chenlin.dp.modules.api.controller;

import net.chenlin.dp.common.constant.MsgConstant;
import net.chenlin.dp.common.constant.SystemConstant;
import net.chenlin.dp.common.constant.XjgjAccApiConstant;
import net.chenlin.dp.common.entity.ResultData;
import net.chenlin.dp.common.utils.EncryptUtils;
import net.chenlin.dp.modules.api.service.BaofooApiService;
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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/app")
public class AppController extends AbstractController {

    private final static Logger logger = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private XjgjAccApiService xjgjService;

    @Autowired
    private BaofooApiService bfService;

    @Autowired
    private MemberInfoService memberInfoService;

    @Autowired
    private MemberBankcardService memberBankService;

    /**
     * 验证APP访问口令
     *
     * @param params
     * @return
     */
    private Boolean checkAccToken(Map<String, Object> params) {
        Object objAccToken = params.get(SystemConstant.ACCESS_TOKEN);
        if (objAccToken == null) {
            return false;
        }
        String access_token = String.valueOf(objAccToken);
        if (null == access_token || "".equals(access_token)) {
            return false;
        }
        String[] arr = access_token.split("_");
        if (arr.length < 2) {
            return false;
        }
        String mobileNum = arr[0];
        String accToken = arr[1];
        MemberInfoEntity memberEntity = memberInfoService.getMemberInfoByNO(String.valueOf(mobileNum));
        if (memberEntity != null) {
            //验证会员账号和密码
            String pwd = memberEntity.getPassword();
            if (accToken.equals(pwd)) {
                //存在token则直接返回
                logger.info("验证app token成功。");
                return true;
            } else {
                logger.info("验证app token失败，token错误。");
                return false;
            }
        } else {
            logger.info("验证app token失败，不是会员。");
            return false;
        }

        /*//使用方法：
        //验证访问口令
        if (!checkAccToken(params)) {
            return new ResultData(MsgConstant.MSG_ERR_ACCESS_TOKEN_CODE, false, MsgConstant.MSG_ERR_ACCESS_TOKEN);
        }*/
    }

    /**
     * 获取APP访问口令
     *
     * @param mobileNum
     * @param pass
     * @return
     */
    @RequestMapping("/getAccToken")
    public ResultData getAccToken(String mobileNum, String pass) {
        try {
            MemberInfoEntity memberEntity = memberInfoService.getMemberInfoByNO(String.valueOf(mobileNum));
            if (memberEntity != null) {
                //验证会员账号和密码
                String memberNO = memberEntity.getMemberId();
                Map<String, Object> param2 = new HashMap<>();
                param2.put(XjgjAccApiConstant.FIELD_MEMBER_NO, memberNO);
                param2.put(XjgjAccApiConstant.FIELD_PASSWORD, pass);
                Map<String, Object> mapResult2 = xjgjService.checkMemberPassword(param2);
                if (mapResult2 != null) {
                    Object result = mapResult2.get(XjgjAccApiConstant.FIELD_RESULT);
                    if (String.valueOf(result) == "1") {//会员账号验证通过
                        //获取访问token
                        String accToken = memberEntity.getPassword();
                        if (null != accToken || !"".equals(accToken)) {
                            //存在token则直接返回
                            logger.info("请求获取app token，成功获取并返回。");
                            return new ResultData("ok", true, accToken);
                        } else {
                            //不存在token则生成新的再返回
                            accToken = EncryptUtils.MD5(mobileNum + "|" + pass);
                            memberInfoService.updateMemberInfo(memberEntity);
                            logger.info("请求获取app token，生成新的值并返回。");
                            return new ResultData("ok", true, accToken);
                        }
                    } else {
                        logger.info("调用结算系统接口验证会员密码，结果：会员密码错误。");
                        return new ResultData("err_password", false, "会员密码错误");
                    }
                } else {
                    logger.info("调用结算系统接口验证会员密码，结果：远程接口出错。");
                    return new ResultData("err_remote", false, MsgConstant.MSG_REMOTE_ERROR);
                }
            } else {
                logger.info("请求获取app token失败，不是会员。");
                return new ResultData("err_no_member", false, "还不是会员");
            }
            /*//通过手机号查询会员账号
            String memberNO = "";
            Map<String, Object> param1 = new HashMap<>();
            param1.put(XjgjAccApiConstant.FIELD_MOBILE_NUM, mobileNum);
            Map<String, Object> mapResult1 = xjgjService.getMemberBaseInfo(param1);
            if (mapResult1 != null) {
                Object objMemberNO = mapResult1.get(XjgjAccApiConstant.FIELD_MEMBER_NO);
                if (null != objMemberNO || !"".equals(objMemberNO)) {
                    memberNO = String.valueOf(objMemberNO);
                } else {
                    return new ResultData("err_no_member", false, "还不是会员");
                }
            } else {
                return new ResultData("err_remote_1", false, MsgConstant.MSG_REMOTE_ERROR);
            }

            //验证会员账号和密码
            Map<String, Object> param2 = new HashMap<>();
            param2.put(XjgjAccApiConstant.FIELD_MEMBER_NO, memberNO);
            param2.put(XjgjAccApiConstant.FIELD_PASSWORD, pass);
            Map<String, Object> mapResult2 = xjgjService.checkMemberPassword(param2);
            if (mapResult2 != null) {
                Object result = mapResult2.get(XjgjAccApiConstant.FIELD_RESULT);
                if (String.valueOf(result) == "1") {//会员账号验证通过
                    //生成访问token
                    String accToken = EncryptUtils.MD5(mobileNum + "|" + pass);
                    return new ResultData("ok", true, accToken);
                } else {
                    return new ResultData("err_password", false, "会员密码错误");
                }
            } else {
                return new ResultData("err_remote_2", false, MsgConstant.MSG_REMOTE_ERROR);
            }
            */
        } catch (Exception e) {
            logger.error(MsgConstant.MSG_OPERATION_FAILED, e);
            return new ResultData("err_exception", false, MsgConstant.MSG_OPERATION_FAILED + e.getMessage());
        }
    }

    /**
     * 查询会员基本信息
     *
     * @param params
     * @return
     */
    @RequestMapping("/getMemberBaseInfo")
    public ResultData getMemberBaseInfo(@RequestBody Map<String, Object> params) {
        try {
            //先在本系统数据库查询会员是否已经绑定APP
            Object objMobile = params.get(XjgjAccApiConstant.FIELD_MOBILE_NUM);
            if (null == objMobile || "".equals(objMobile)) {
                return new ResultData("err_param", false, MsgConstant.MSG_OPERATION_FAILED);
            }
            String mobile = String.valueOf(objMobile);
            MemberInfoEntity memberEntity = memberInfoService.getMemberInfoByMobile(mobile);

            Map<String, Object> mapResult = xjgjService.getMemberBaseInfo(params);
            if (mapResult != null) {
                if (memberEntity != null) {//已经绑定APP
                    mapResult.put(MsgConstant.MSG_IS_APP_BOUND, true);
                } else {
                    mapResult.put(MsgConstant.MSG_IS_APP_BOUND, false);
                }
                return new ResultData("ok", true, MsgConstant.MSG_OPERATION_SUCCESS, mapResult);
            } else {
                return new ResultData("err_remote", false, MsgConstant.MSG_REMOTE_ERROR);
            }
        } catch (Exception e) {
            logger.error(MsgConstant.MSG_OPERATION_FAILED, e);
            return new ResultData("err_exception", false, MsgConstant.MSG_OPERATION_FAILED + e.getMessage(), "");
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
                return new ResultData("ok", true, MsgConstant.MSG_OPERATION_SUCCESS, mapResult);
            } else {
                return new ResultData("err_remote", false, MsgConstant.MSG_REMOTE_ERROR, "");
            }
        } catch (Exception e) {
            logger.error(MsgConstant.MSG_OPERATION_FAILED, e);
            return new ResultData("err_exception", false, MsgConstant.MSG_OPERATION_FAILED + e.getMessage(), "");
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
            Map<String, Object> mapResult = xjgjService.memberAppBind(params);
            return memberAppRegister(1, mapResult, params);
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
            Map<String, Object> mapResult = xjgjService.regMember(params);
            return memberAppRegister(0, mapResult, params);
        } catch (Exception e) {
            logger.error(MsgConstant.MSG_OPERATION_FAILED, e);
            return new ResultData("e1", false, MsgConstant.MSG_OPERATION_FAILED + e.getMessage(), "");
        }
    }

    /**
     * 新会员注册或老会员绑定
     *
     * @param opration  0-注册；1-绑定
     * @param mapResult 接口返回结果
     * @param params    请求参数
     * @return
     */
    public ResultData memberAppRegister(int opration, Map<String, Object> mapResult, Map<String, Object> params) {
        try {
            Object memberNO = null;
            if (params != null) {
                //必要的后台验证
                if (opration == 1) {//绑定
                    memberNO = params.get(XjgjAccApiConstant.FIELD_MEMBER_NO);//会员账号（会员编号）
                    if (null == memberNO || "".equals(memberNO)) {
                        return new ResultData("err_memberNO_isnull", false, "会员账号不能为空！");
                    }
                }
                Object memberName = params.get(XjgjAccApiConstant.FIELD_MEMBER_NAME);//会员姓名
                if (null == memberName || "".equals(memberName)) {
                    return new ResultData("err_memberName_isnull", false, "会员姓名不能为空！");
                }
                Object idCard = params.get(XjgjAccApiConstant.FIELD_ID_CARD);//身份证号
                if (null == idCard || "".equals(idCard)) {
                    return new ResultData("err_idCard_isnull", false, "身份证号不能为空！");
                }
                Object mobileNum = params.get(XjgjAccApiConstant.FIELD_MOBILE_NUM);//手机号
                if (null == mobileNum || "".equals(mobileNum)) {
                    return new ResultData("err_mobileNum_isnull", false, "银行预留手机号不能为空！");
                }
                Object pass = params.get(XjgjAccApiConstant.FIELD_PASSWORD);//会员密码
                if (null == pass || "".equals(pass)) {
                    return new ResultData("err_password_isnull", false, "会员密码不能为空！");
                }

                Object memberAddr = params.get(XjgjAccApiConstant.FIELD_MEMBER_ADDRESS);//会员地址
                Object toCorpAddr = params.get(XjgjAccApiConstant.FIELD_TO_CORP_ADDRESS);//会员商品去向地址(西郊)

                if (mapResult != null) {
                    if (opration == 0) {//新注册
                        memberNO = mapResult.get(XjgjAccApiConstant.FIELD_MEMBER_NO);
                    }
                    Object resultState = mapResult.get(XjgjAccApiConstant.FIELD_RESULT);// 1-成功,0-失败,2-已经绑定过APP
                    Object message = mapResult.get(XjgjAccApiConstant.FIELD_MESSAGE);
                    //Object isQtBinded = mapResult.get(XjgjAccApiConstant.FIELD_IS_QT_BINDED);
                    //Object bankAccount = mapResult.get(XjgjAccApiConstant.FIELD_BANK_ACCOUNT);
                    Object mCardNO = mapResult.get(XjgjAccApiConstant.FIELD_MEMBER_CARD_NO);
                    if ("1".equals(resultState) || "2".equals(resultState)) {//调用结算系统会员绑定接口处理成功
                        //保存会员注册填写的信息到数据库
                        MemberInfoEntity memberEntity = memberInfoService.getMemberInfoByNO(String.valueOf(memberNO));
                        Boolean isNew = false;
                        Date now = new Date();
                        if (memberEntity == null) {
                            memberEntity = new MemberInfoEntity();
                            memberEntity.setGmtCreate(now);
                            isNew = true;
                        } else {
                            isNew = false;
                            memberEntity.setGmtModified(now);
                        }

                        memberEntity.setMemberId(String.valueOf(memberNO));
                        memberEntity.setMemberType(0);//默认为普通（个人买方）
                        memberEntity.setMemberName(String.valueOf(memberName));
                        memberEntity.setIdCard(String.valueOf(idCard));
                        memberEntity.setIdCardType(1);//默认为身份证
                        memberEntity.setMobile(String.valueOf(mobileNum));

                        String accToken = EncryptUtils.MD5(mobileNum + "|" + pass);
                        memberEntity.setPassword(accToken);

                        if (null != mCardNO || !"".equals(mCardNO))
                            memberEntity.setCardId(String.valueOf(mCardNO));

                        if (null != memberAddr || !"".equals(memberAddr))
                            memberEntity.setMemberAddress(String.valueOf(memberAddr));

                        if (null != toCorpAddr || !"".equals(toCorpAddr))
                            memberEntity.setToCorpAddress(String.valueOf(toCorpAddr));

                        memberEntity.setIsAvailable(1);

                        if (isNew) {
                            memberInfoService.saveMemberInfo(memberEntity);
                        } else {
                            memberInfoService.updateMemberInfo(memberEntity);
                        }

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

    //宝付预绑卡
    @RequestMapping("/preBindBaofoo")
    public ResultData preBindBaofoo(Map<String, String> params) {
        try {
            Map<String, Object> mapResult = bfService.backTrans(params);
            return new ResultData("ok", true, MsgConstant.MSG_OPERATION_SUCCESS, mapResult);
        } catch (Exception e) {
            logger.error(MsgConstant.MSG_OPERATION_FAILED, e);
            return new ResultData("err_exception", false, MsgConstant.MSG_OPERATION_FAILED + e.getMessage());
        }
    }

    /**
     * 会员圈提绑定
     *
     * @param params
     * @return
     */
    @RequestMapping("/memberBindBOC")
    public ResultData memberBindBOC(@RequestBody Map<String, Object> params) {
        try {
            Map<String, Object> mapResult = xjgjService.memberBindBOC(params);
            if (mapResult != null) {
                return new ResultData("ok", true, MsgConstant.MSG_OPERATION_SUCCESS, mapResult);
            } else {
                return new ResultData("err", false, MsgConstant.MSG_OPERATION_FAILED, "");
            }
        } catch (Exception e) {
            logger.error(MsgConstant.MSG_OPERATION_FAILED, e);
            return new ResultData("err", false, MsgConstant.MSG_OPERATION_FAILED + e.getMessage(), "");
        }
    }

    /**
     * 会员圈提解绑
     *
     * @param
     * @return
     */
    @RequestMapping("/memberUnBindBOC")
    public ResultData memberUnBindBOC(@RequestBody Map<String, Object> params) {
        try {
            Map<String, Object> mapResult = xjgjService.memberUnBindBOC(params);
            if (mapResult != null) {
                return new ResultData("ok", true, MsgConstant.MSG_OPERATION_SUCCESS, mapResult);
            } else {
                return new ResultData("err", false, MsgConstant.MSG_OPERATION_FAILED, "");
            }
        } catch (Exception e) {
            logger.error(MsgConstant.MSG_OPERATION_FAILED, e);
            return new ResultData("err", false, MsgConstant.MSG_OPERATION_FAILED, "");
        }
    }

    /**
     * 会员圈提
     *
     * @param params
     * @return
     */
    @RequestMapping("/memberWithDraw")
    public ResultData memberWithDraw(@RequestBody Map<String, Object> params) {
        //Object result = null;
        String accountBalancekey = "accountQty";
        String drawKey = "moneyQty";
        try {
            Map<String, Object> map = xjgjService.memberWithDraw(params);
            Map<String, Object> momenyBalanceMap = xjgjService.searchMemberAccountBalance(params.get("memberNo").toString());
            //TODO 会员圈提之前先判断是否绑定银行卡
            int accountBalance = (int) momenyBalanceMap.get(accountBalancekey);
            int drawMoney = (int) params.get(drawKey);
            if (accountBalance <= 0 && drawMoney > accountBalance) {
                return new ResultData("err", false, "账户余额不足！", "");
            }
            return new ResultData("ok", true, "查询成功", map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultData("e1", false, MsgConstant.MSG_OPERATION_FAILED + e.getMessage(), "");
        }

    }

    /**
     * 查询账户余额
     *
     * @param param
     * @reutn
     */
    @RequestMapping("/searchMemberAccountBalance")
    public ResultData searchMemberAccountBalance(@RequestBody String param) {
        try {
            Map<String, Object> mapResult = xjgjService.searchMemberAccountBalance(param);
            return new ResultData("ok", true, MsgConstant.MSG_OPERATION_SUCCESS, mapResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultData("e1", false, MsgConstant.MSG_OPERATION_FAILED + e.getMessage(), "");
        }
    }


    /**
     * 查询一段时间账户余额变动
     *
     * @param map
     * @return
     */
    @RequestMapping("/searchMemberAccountChange")
    public ResultData searchMemberAccountChangeByPeriodOfTime(Map<String, Object> map) {
        try {
            Map<String, Object> mapResult = xjgjService.searchMemberCostLog(map);
            return new ResultData("ok", true, MsgConstant.MSG_OPERATION_SUCCESS, mapResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultData("e1", false, MsgConstant.MSG_OPERATION_FAILED + e.getMessage(), "");
        }
    }

}
