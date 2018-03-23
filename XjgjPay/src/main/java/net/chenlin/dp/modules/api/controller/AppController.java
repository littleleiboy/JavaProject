package net.chenlin.dp.modules.api.controller;

import net.chenlin.dp.common.constant.BaofooApiConstant;
import net.chenlin.dp.common.constant.MsgConstant;
import net.chenlin.dp.common.constant.SystemConstant;
import net.chenlin.dp.common.constant.XjgjAccApiConstant;
import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.ResultData;
import net.chenlin.dp.common.utils.EncryptUtils;
import net.chenlin.dp.common.utils.IPUtils;
import net.chenlin.dp.common.utils.JacksonUtils;
import net.chenlin.dp.common.utils.OrderNumberUtils;
import net.chenlin.dp.modules.api.service.BaofooApiService;
import net.chenlin.dp.modules.api.service.XjgjAccApiService;
import net.chenlin.dp.modules.base.entity.MemberBankcardEntity;
import net.chenlin.dp.modules.base.entity.MemberInfoEntity;
import net.chenlin.dp.modules.base.service.MemberBankcardService;
import net.chenlin.dp.modules.base.service.MemberInfoService;
import net.chenlin.dp.modules.sys.controller.AbstractController;
import net.chenlin.dp.modules.sys.service.DicBankService;
import net.chenlin.dp.modules.trade.entity.TradeLogEntity;
import net.chenlin.dp.modules.trade.service.TradeLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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

    @Autowired
    private TradeLogService tradeLogService;

    @Autowired
    private DicBankService dicBankService;

    /**
     * 验证APP访问口令
     *
     * @param params
     * @return
     */
    private Boolean checkObjAccToken(Map<String, Object> params) {
        Object objAccToken = params.get(SystemConstant.ACCESS_TOKEN);
        if (objAccToken == null) {
            return false;
        }
        String access_token = String.valueOf(objAccToken);
        if (null == access_token || "".equals(access_token)) {
            return false;
        }
        return checkAccessToken(access_token);
    }

    /**
     * 验证APP访问口令
     *
     * @param params
     * @return
     */
    private Boolean checkStrAccToken(Map<String, String> params) {
        String access_token = params.get(SystemConstant.ACCESS_TOKEN);
        if (null == access_token || "".equals(access_token)) {
            return false;
        }
        return checkAccessToken(access_token);
    }

    /**
     * 验证APP访问口令
     *
     * @param access_token
     * @return
     */
    private Boolean checkAccessToken(String access_token) {
        String[] arr = access_token.split("_");
        if (arr.length < 2) {
            return false;
        }
        String mobileNum = arr[0];
        String accToken = arr[1];
        MemberInfoEntity memberEntity = memberInfoService.getMemberInfoByMobile(String.valueOf(mobileNum));
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
        if (!checkAccessToken(acc_token)) {
            return new ResultData(MsgConstant.MSG_ERR_ACCESS_TOKEN_CODE, false, MsgConstant.MSG_ERR_ACCESS_TOKEN);
        }*/
    }

    /**
     * APP用户登录后获取API访问口令和用户身份信息
     *
     * @param mobileNum
     * @param pass
     * @return
     */
    @RequestMapping("/appLogin")
    public ResultData appLogin(String mobileNum, String pass) {
        try {
            MemberInfoEntity memberEntity = memberInfoService.getMemberInfoByMobile(mobileNum);
            if (memberEntity != null) {
                //验证会员账号和密码
                String memberNO = memberEntity.getMemberId();
                Map<String, Object> param2 = new HashMap<>();
                param2.put(XjgjAccApiConstant.FIELD_MEMBER_NO, memberNO);
                param2.put(XjgjAccApiConstant.FIELD_PASSWORD, pass);
                Map<String, Object> mapResult2 = xjgjService.checkMemberPassword(param2);
                if (mapResult2 != null) {
                    Object result = mapResult2.get(XjgjAccApiConstant.FIELD_RESULT);
                    if ("1".equals(result)) {//会员账号验证通过
                        //获取访问token
                        String accToken = memberEntity.getPassword();
                        if (null != accToken || !"".equals(accToken)) {
                            //存在token则直接返回
                            logger.info("手机号" + mobileNum + "的用户登录APP成功，获取到已存在的token.");
                        } else {
                            //不存在token则生成新的再返回
                            accToken = EncryptUtils.MD5(mobileNum + "|" + pass);
                            memberInfoService.updateMemberInfo(memberEntity);
                            logger.info("手机号" + mobileNum + "的用户登录APP成功，获取到新的token.");
                        }

                        //返回的用户身份信息
                        Map<String, Object> appUser = new HashMap<>();
                        appUser.put(SystemConstant.MEMBER_PK, memberEntity.getId());
                        appUser.put(SystemConstant.MEMBER_NO, memberEntity.getMemberId());
                        appUser.put(SystemConstant.MEMBER_NAME, memberEntity.getMemberName());
                        appUser.put(SystemConstant.MEMBER_MOBILE, memberEntity.getMobile());
                        appUser.put(SystemConstant.ACCESS_TOKEN, accToken);

                        return new ResultData("ok", true, accToken, appUser);
                    } else {
                        logger.info("调用结算系统接口验证会员密码，结果：会员密码错误。");
                        return new ResultData("err_password", false, "账号或密码错误");
                    }
                } else {
                    logger.info("调用结算系统接口验证会员密码，结果：远程接口出错。");
                    return new ResultData("err_remote", false, MsgConstant.MSG_REMOTE_ERROR);
                }
            } else {
                logger.info("请求获取app token失败，不是会员。");
                return new ResultData("err_no_member", false, "还不是会员");
            }
        } catch (Exception e) {
            logger.error(MsgConstant.MSG_SERVER_ERROR, e);
            return new ResultData("err_exception", false, MsgConstant.MSG_SERVER_ERROR);
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
                if (memberEntity != null) {
                    //有会员账号才算已绑定APP
                    String memberNo = memberEntity.getMemberId();
                    if (null != memberNo && !"".equals(memberNo))
                        mapResult.put(MsgConstant.MSG_IS_APP_BOUND, true);
                    else
                        mapResult.put(MsgConstant.MSG_IS_APP_BOUND, false);
                } else {
                    mapResult.put(MsgConstant.MSG_IS_APP_BOUND, false);
                }
                return new ResultData("ok", true, MsgConstant.MSG_OPERATION_SUCCESS, mapResult);
            } else {
                return new ResultData("err_remote", false, MsgConstant.MSG_REMOTE_ERROR);
            }
        } catch (Exception e) {
            logger.error(MsgConstant.MSG_SERVER_ERROR, e);
            return new ResultData("err_exception", false, MsgConstant.MSG_SERVER_ERROR);
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
            logger.error(MsgConstant.MSG_SERVER_ERROR, e);
            return new ResultData("err_exception", false, MsgConstant.MSG_SERVER_ERROR);
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
            logger.error(MsgConstant.MSG_SERVER_ERROR, e);
            return new ResultData("err_exception", false, MsgConstant.MSG_SERVER_ERROR);
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
            logger.error(MsgConstant.MSG_SERVER_ERROR, e);
            return new ResultData("e1", false, MsgConstant.MSG_SERVER_ERROR);
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
                            isNew = true;
                        } else {
                            isNew = false;
                        }

                        memberEntity.setMemberId(String.valueOf(memberNO));
                        memberEntity.setMemberType(0);//默认为普通（个人买方）
                        memberEntity.setMemberName(String.valueOf(memberName));
                        memberEntity.setIdCard(String.valueOf(idCard));
                        memberEntity.setIdCardType(1);//默认为身份证
                        memberEntity.setMobile(String.valueOf(mobileNum));

                        String accToken = EncryptUtils.MD5(mobileNum + "|" + pass);
                        memberEntity.setPassword(accToken);
                        memberEntity.setEmail("");

                        if (null != mCardNO && !"".equals(mCardNO))
                            memberEntity.setCardId(String.valueOf(mCardNO));
                        else
                            memberEntity.setCardId("");

                        if (null != memberAddr && !"".equals(memberAddr))
                            memberEntity.setMemberAddress(String.valueOf(memberAddr));
                        else
                            memberEntity.setMemberAddress("");

                        if (null != toCorpAddr && !"".equals(toCorpAddr))
                            memberEntity.setToCorpAddress(String.valueOf(toCorpAddr));
                        else
                            memberEntity.setToCorpAddress("");

                        memberEntity.setIsAvailable(new Byte("1"));
                        memberEntity.setRemark("");

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
            logger.error(MsgConstant.MSG_SERVER_ERROR, e);
            return new ResultData("err_exception", false, MsgConstant.MSG_SERVER_ERROR);
        }
    }

    /**
     * 绑定宝付申请
     *
     * @param params
     * @return
     */
    @RequestMapping("/preBindBaofoo")
    public ResultData preBindBaofoo(@RequestBody Map<String, String> params) {
        try {
            //验证token
            if (!checkAccessToken(params.get(SystemConstant.ACCESS_TOKEN))) {
                return new ResultData(MsgConstant.MSG_ERR_ACCESS_TOKEN_CODE, false, MsgConstant.MSG_ERR_ACCESS_TOKEN);
            }

            //宝付预绑卡类交易
            params.put(BaofooApiConstant.FIELD_TXN_SUB_TYPE, BaofooApiConstant.TradeType.prepareBinding.getValue());
            params.put(BaofooApiConstant.FIELD_TRANS_SERIAL_NO, OrderNumberUtils.generateInTime());//商户流水号
            params.put(BaofooApiConstant.FIELD_TRANS_ID, OrderNumberUtils.generateInTime());//商户订单号

            //调用宝付接口
            Map<String, Object> mapResult = bfService.backTrans(params);
            if (mapResult != null) {
                if (BaofooApiConstant.RESP_CODE_SUCCESS.equals(mapResult.get(BaofooApiConstant.FIELD_RESP_CODE))) {
                    return new ResultData("ok", true, MsgConstant.MSG_OPERATION_SUCCESS, mapResult);
                } else {
                    return new ResultData("err_bf", false, MsgConstant.MSG_OPERATION_FAILED + "(" + mapResult.get(BaofooApiConstant.FIELD_RESP_MSG) + ")", mapResult);
                }
            } else {
                return new ResultData("err_response", false, MsgConstant.MSG_OPERATION_FAILED);
            }
        } catch (Exception e) {
            logger.error(MsgConstant.MSG_SERVER_ERROR, e);
            return new ResultData("err_exception", false, MsgConstant.MSG_SERVER_ERROR);
        }
    }

    /**
     * 圈存解绑
     *
     * @param params
     * @return
     */
    @RequestMapping("/cancelBinding")
    public ResultData cancelBinding(@RequestBody Map<String, String> params) {
        try {
            //验证token
            if (!checkAccessToken(params.get(SystemConstant.ACCESS_TOKEN))) {
                return new ResultData(MsgConstant.MSG_ERR_ACCESS_TOKEN_CODE, false, MsgConstant.MSG_ERR_ACCESS_TOKEN);
            }

            //解除绑定关系
            params.put(BaofooApiConstant.FIELD_TXN_SUB_TYPE, BaofooApiConstant.TradeType.cancelBinding.getValue());

            params.put(BaofooApiConstant.FIELD_TRANS_SERIAL_NO, OrderNumberUtils.generateInTime());//商户流水号

            String acc_no = params.get(BaofooApiConstant.FIELD_ACC_NO);
            if (null == acc_no || "".equals(acc_no)) {
                return new ResultData("err_acc_no_isnull", false, "请求解绑的银行卡号不能为空！");
            }

            // 调用宝付接口解除绑定交易
            Map<String, Object> mapResult = bfService.backTrans(params);
            if (mapResult != null) {
                if (BaofooApiConstant.RESP_CODE_SUCCESS.equals(mapResult.get(BaofooApiConstant.FIELD_RESP_CODE))) {
                    Object bindId = params.get(BaofooApiConstant.FIELD_BIND_ID);
                    if (bindId != null || !"".equals(bindId)) {
                        MemberBankcardEntity memberBankcardInfo = memberBankService.getBankcardByBankCardID(acc_no);
                        if (memberBankcardInfo != null) {
                            //更新数据库该银行卡的信息
                            memberBankService.updateMemberBankCardInfo(memberBankcardInfo.getId());
                            return new ResultData("ok", true, "解除绑定关系成功", mapResult);
                        } else {
                            return new ResultData("ok", true, "解除绑定关系成功", mapResult);
                        }
                    } else {
                        return new ResultData("err_remote", false, MsgConstant.MSG_REMOTE_ERROR);
                    }
                }
                return new ResultData("err_response", false, MsgConstant.MSG_OPERATION_FAILED);
            } else {
                return new ResultData("err_response", false, MsgConstant.MSG_OPERATION_FAILED);
            }

        } catch (Exception e) {
            logger.error(MsgConstant.MSG_SERVER_ERROR, e);
            return new ResultData("err_exception", false, MsgConstant.MSG_SERVER_ERROR);
        }
    }

    /**
     * 确认绑定宝付
     *
     * @param params
     * @return
     */
    @RequestMapping("/confirmBindBaofoo")
    public ResultData confirmBindBaofoo(@RequestBody Map<String, String> params) {
        try {
            //验证token
            if (!checkAccessToken(params.get(SystemConstant.ACCESS_TOKEN))) {
                return new ResultData(MsgConstant.MSG_ERR_ACCESS_TOKEN_CODE, false, MsgConstant.MSG_ERR_ACCESS_TOKEN);
            }

            //宝付确认绑卡类交易
            params.put(BaofooApiConstant.FIELD_TXN_SUB_TYPE, BaofooApiConstant.TradeType.confirmBinding.getValue());

            params.put(BaofooApiConstant.FIELD_TRANS_SERIAL_NO, OrderNumberUtils.generateInTime());//商户流水号
            //params.put(BaofooApiConstant.FIELD_TRANS_ID, OrderNumberUtils.generateInTime());//商户订单号

            String memberPK = params.get(SystemConstant.MEMBER_PK);//会员账号（会员编号）
            if (null == memberPK || "".equals(memberPK)) {
                return new ResultData("err_memberPK_isnull", false, "会员标识不能为空！");
            }
            Long memberId = Long.valueOf(memberPK);

            String bankCardID = params.get(BaofooApiConstant.FIELD_ACC_NO);
            if (null == bankCardID || "".equals(bankCardID)) {
                return new ResultData("err_acc_no_isnull", false, "请求绑定的银行卡号不能为空！");
            }
            //商户订单号不能为空
            String trans_id = params.get(BaofooApiConstant.FIELD_TRANS_ID);
            if (null == trans_id || "".equals(trans_id)) {
                return new ResultData("err_trans_id", false, BaofooApiConstant.MSG_REQUIRE_TRANS_ID);
            }
            String accName = params.get(BaofooApiConstant.FIELD_ID_HOLDER);
            if (null == accName || "".equals(accName)) {
                return new ResultData("err_id_holder", false, "银行账户姓名不能为空");
            }
            String payCode = params.get(BaofooApiConstant.FIELD_PAY_CODE);
            if (null == payCode || "".equals(payCode)) {
                return new ResultData("err_id_paycode", false, "银行编号不能为空");
            }
            //短信验证码不能为空
            String sms_code = params.get(BaofooApiConstant.FIELD_SMS_CODE);//短信验证码
            if (null == sms_code || "".equals(sms_code)) {
                return new ResultData("err_sms_code", false, BaofooApiConstant.MSG_REQUIRE_SMS_CODE);
            }
            //调用宝付接口
            Map<String, Object> mapResult = bfService.backTrans(params);
            if (mapResult != null) {
                if (BaofooApiConstant.RESP_CODE_SUCCESS.equals(mapResult.get(BaofooApiConstant.FIELD_RESP_CODE))) {
                    //宝付接口返回成功消息
                    Object bindId = mapResult.get(BaofooApiConstant.FIELD_BIND_ID);
                    if (null != bindId || !"".equals(bindId)) {
                        //保存会员绑定宝付信息
                        MemberBankcardEntity mbank = memberBankService.getBankcardByBankCardID(bankCardID);
                        Boolean isNew = false;
                        if (mbank != null) {
                            //更新会员的宝付绑定银行卡信息
                            isNew = false;
                            mbank.setGmtModified(new Date());
                        } else {
                            //新增会员的宝付绑定银行卡信息
                            isNew = true;
                            mbank = new MemberBankcardEntity();
                            mbank.setGmtCreate(new Date());
                            mbank.setIsWithdraw(0);//默认不可提现(不可圈提)
                        }

                        mbank.setMemberInfoId(memberId);
                        mbank.setIsRecharge(1);//可充值(可圈存)
                        mbank.setBfBindId(String.valueOf(bindId));
                        mbank.setBankAccCard(bankCardID);
                        mbank.setBankAccName(accName);
                        mbank.setBankCode(payCode);

                        MemberInfoEntity member = memberInfoService.getMemberInfoEntityById(memberId);
                        if (member != null)
                            mbank.setMemberInfoId(member.getId());

                        if (isNew)
                            memberBankService.saveMemberBankcard(mbank);
                        else
                            memberBankService.updateMemberBankcard(mbank);

                        return new ResultData("ok", true, MsgConstant.MSG_OPERATION_SUCCESS, mapResult);
                    } else {
                        return new ResultData("err_remote", false, MsgConstant.MSG_REMOTE_ERROR);
                    }
                } else {
                    logger.info("宝付确认绑卡失败！" + mapResult.get(BaofooApiConstant.FIELD_RESP_MSG));
                    return new ResultData("err_bf", false, MsgConstant.MSG_OPERATION_FAILED + "(" + mapResult.get(BaofooApiConstant.FIELD_RESP_MSG) + ")", mapResult);
                }
            } else {
                return new ResultData("err_response", false, MsgConstant.MSG_OPERATION_FAILED);
            }
        } catch (Exception e) {
            logger.error(MsgConstant.MSG_SERVER_ERROR, e);
            return new ResultData("err_exception", false, MsgConstant.MSG_SERVER_ERROR);
        }
    }


    /**
     * 会员圈存（充值）预交易
     *
     * @param params
     * @return
     */
    @RequestMapping("/preRecharge")
    public ResultData preRecharge(HttpServletRequest request, @RequestBody Map<String, String> params) {
        try {
            //验证token
            if (!checkAccessToken(params.get(SystemConstant.ACCESS_TOKEN))) {
                return new ResultData(MsgConstant.MSG_ERR_ACCESS_TOKEN_CODE, false, MsgConstant.MSG_ERR_ACCESS_TOKEN);
            }
            //宝付认证支付类预支付交易
            params.put(BaofooApiConstant.FIELD_TXN_SUB_TYPE, BaofooApiConstant.TradeType.preparePay.getValue());

            params.put(BaofooApiConstant.FIELD_TRANS_SERIAL_NO, OrderNumberUtils.generateInTime());//商户流水号
            params.put(BaofooApiConstant.FIELD_TRANS_ID, OrderNumberUtils.generateInTime());//商户订单号
            String txn_amt = params.get(BaofooApiConstant.FIELD_TXN_AMT);//以元为单位的支付金额
            if (null == txn_amt || "".equals(txn_amt)) {
                return new ResultData("err_txn_amt_isnull", false, "交易金额不能为空！");
            }
            String bind_id = params.get(BaofooApiConstant.FIELD_BIND_ID);//绑定标识
            if (null == bind_id || "".equals(bind_id)) {
                return new ResultData("err_bind_id_isnull", false, "绑定标识号不能为空！");
            }

            BigDecimal txn_amt_num = new BigDecimal(txn_amt).multiply(BigDecimal.valueOf(100));//金额转换成分
            params.put(BaofooApiConstant.FIELD_TXN_AMT, String.valueOf(txn_amt_num.setScale(0)));//以分为单位的支付金额
            params.put(BaofooApiConstant.FIELD_CLIENT_IP, IPUtils.getIpAddr(request));

            //调用宝付接口
            Map<String, Object> mapResult = bfService.backTrans(params);
            if (mapResult != null) {
                logger.info("宝付预支付处理返回结果：" + JacksonUtils.beanToJson(mapResult));
                if (BaofooApiConstant.RESP_CODE_SUCCESS.equals(mapResult.get(BaofooApiConstant.FIELD_RESP_CODE))) {
                    logger.info(String.format("宝付预支付交易处理成功，申请交易金额：%s 元，宝付业务流水号：%s 。", txn_amt, String.valueOf(mapResult.get(BaofooApiConstant.FIELD_BUSINESS_NO))));
                    return new ResultData("ok", true, MsgConstant.MSG_OPERATION_SUCCESS, mapResult);
                } else {
                    String error = MsgConstant.MSG_OPERATION_FAILED + " " + String.valueOf(mapResult.get(BaofooApiConstant.FIELD_RESP_MSG));
                    return new ResultData("err_response_baofoo", false, error);
                }
            } else {
                return new ResultData("err_remote", false, MsgConstant.MSG_OPERATION_FAILED);
            }
        } catch (Exception e) {
            logger.error(MsgConstant.MSG_SERVER_ERROR, e);
            return new ResultData("err_exception", false, MsgConstant.MSG_SERVER_ERROR);
        }
    }

    /**
     * 确认会员圈存（充值）交易
     *
     * @param params
     * @return
     */
    @RequestMapping("/recharge")
    public ResultData recharge(@RequestBody Map<String, String> params) {
        try {
            //验证token
            if (!checkAccessToken(params.get(SystemConstant.ACCESS_TOKEN))) {
                return new ResultData(MsgConstant.MSG_ERR_ACCESS_TOKEN_CODE, false, MsgConstant.MSG_ERR_ACCESS_TOKEN);
            }
            String trans_serial_no = OrderNumberUtils.generateInTime();
            String trans_id = OrderNumberUtils.generateInTime();
            params.put(BaofooApiConstant.FIELD_TRANS_SERIAL_NO, trans_serial_no);//商户流水号(每次请求都不可重复)
            params.put(BaofooApiConstant.FIELD_TRANS_ID, trans_id);//商户订单号
            params.put(BaofooApiConstant.FIELD_TXN_SUB_TYPE, BaofooApiConstant.TradeType.confirmPay.getValue());//宝付认证支付类确认支付交易
            //String sms_code = params.get(BaofooApiConstant.FIELD_SMS_CODE);//支付短信验证码
            String business_no = params.get(BaofooApiConstant.FIELD_BUSINESS_NO);//宝付业务流水号
            if (null == business_no || "".equals(business_no)) {
                return new ResultData("err_business_no_isnull", false, "支付业务流水号不能为空！");
            }
            //保存交易记录
            TradeLogEntity trade = new TradeLogEntity();
            trade.setTransSn(business_no);//圈存交易流水号和宝付支付流水号一致
            trade.setTransType(SystemConstant.TradeType.RECHARGE.getValue());
            trade.setSellerOrderId(trans_id);
            trade.setPayModeId(SystemConstant.PayMode.BAOFOO.getValue());

            String bankCode = "";
            String bindId = params.get(BaofooApiConstant.FIELD_BIND_ID);
            if (null != bindId || !"".equals(bindId)) {
                MemberBankcardEntity bankCard = memberBankService.getBankcardByBfBindID(bindId);
                if (bankCard != null) {
                    trade.setBankAccName(bankCard.getBankAccName());
                    trade.setBankAccCard(bankCard.getBankAccCard());
                    trade.setBankCode(bankCard.getBankCode());
                    bankCode = bankCard.getBankCode();
                }
                trade.setBfBindId(bindId);
            }
            trade.setGmtCreate(new Date());
            BigDecimal txnAmt = new BigDecimal(String.valueOf(params.get(XjgjAccApiConstant.FIELD_MONEY)));

            //计算手续费
            BigDecimal poundage = xjgjService.poundageRecharge(txnAmt, bankCode);//手续费
            BigDecimal rechargeAmount = txnAmt.subtract(poundage);//不含手续费金额
            params.put(XjgjAccApiConstant.FIELD_MONEY, String.valueOf(rechargeAmount));//更新扣除手续费的实际充值金额

            //调用宝付接口
            Map<String, Object> mapBfResult = bfService.backTrans(params);
            if (mapBfResult != null) {
                logger.info("宝付确认支付处理返回结果：" + JacksonUtils.beanToJson(mapBfResult));
                mapBfResult.put(XjgjAccApiConstant.FIELD_OLDREQUEST_NO, trade.getSellerOrderId());
                if (BaofooApiConstant.RESP_CODE_SUCCESS.equals(mapBfResult.get(BaofooApiConstant.FIELD_RESP_CODE))) {
                    //宝付接口返回成功消息
                    BigDecimal succAmt = new BigDecimal(String.valueOf(mapBfResult.get(BaofooApiConstant.FIELD_SUCC_AMT))).divide(BigDecimal.valueOf(100));//把分转换成元
                    logger.info(String.format("宝付确认支付交易处理成功，宝付支付业务流水号：%s，圈存金额：%s 元，西郊圈存手续费：%s 元。",
                            mapBfResult.get(BaofooApiConstant.FIELD_BUSINESS_NO),
                            succAmt, poundage));
                    mapBfResult.put(XjgjAccApiConstant.FIELD_POUNDAGE, poundage);//西郊圈存手续费
                    mapBfResult.put(XjgjAccApiConstant.FIELD_ACTUAL_AMOUNT, succAmt);//支付成功的不含手续费圈存金额

                    trade.setTransSn(String.valueOf(mapBfResult.get(BaofooApiConstant.FIELD_BUSINESS_NO)));//圈存交易流水号和宝付支付流水号一致
                    trade.setAmtMoney(succAmt);

                    //调用西郊结算系统接口记录圈存交易
                    Map<String, Object> xjParams = new HashMap<>();
                    xjParams.put(XjgjAccApiConstant.FIELD_MEMBER_NO, params.get(XjgjAccApiConstant.FIELD_MEMBER_NO));
                    xjParams.put(XjgjAccApiConstant.FIELD_MEMBER_NAME, params.get(XjgjAccApiConstant.FIELD_MEMBER_NAME));
                    xjParams.put(XjgjAccApiConstant.FIELD_REQUEST_NO, trade.getSellerOrderId());
                    xjParams.put(XjgjAccApiConstant.FIELD_PASSWORD, params.get(XjgjAccApiConstant.FIELD_PASSWORD));
                    xjParams.put(XjgjAccApiConstant.FIELD_BF_ORDERNO, business_no);
                    xjParams.put(XjgjAccApiConstant.FIELD_XJ_ORDERNO, trans_id);
                    xjParams.put(XjgjAccApiConstant.FIELD_BF_TRADENO, trans_serial_no);
                    String strMoney = params.get(XjgjAccApiConstant.FIELD_MONEY);
                    if (null == strMoney || "".equals(strMoney)) {
                        return new ResultData("err_money_isnull", false, "交易金额不能为空！");
                    }
                    BigDecimal numMoney = new BigDecimal(strMoney).multiply(BigDecimal.valueOf(100));//金额元转换成分
                    xjParams.put(XjgjAccApiConstant.FIELD_MONEY, String.valueOf(numMoney.setScale(0)));
                    Map<String, Object> mapXjResult = xjgjService.recharge(xjParams);
                    if (mapXjResult != null) {
                        if ("1".equals(mapXjResult.get(XjgjAccApiConstant.FIELD_RESULT))) {//西郊结算返回成功消息
                            trade.setState(SystemConstant.RECHARGE_STATE_SUCCESS);//支付成功且结算成功
                            tradeLogService.saveTradeLog(trade);

                            logger.info("宝付确认支付成功！西郊国际结算处理成功！交易流水号："
                                    + String.valueOf(mapBfResult.get(BaofooApiConstant.FIELD_BUSINESS_NO)) + "。");

                            //支付并结算成功提示消息
                            String successMsg = String.format("圈存成功！圈存金额：%s元，手续费：%s元。", succAmt, poundage);
                            return new ResultData("ok", true, successMsg, mapBfResult);
                        } else {
                            trade.setState(SystemConstant.RECHARGE_STATE_PAY_OK);//支付成功但结算失败(宝付交易成功，西郊结算系统处理失败)
                            tradeLogService.saveTradeLog(trade);

                            logger.info("宝付确认支付成功！西郊国际结算处理失败！交易流水号："
                                    + String.valueOf(mapBfResult.get(BaofooApiConstant.FIELD_BUSINESS_NO)) + "。");
                            return new ResultData("err_bf1xj0", false, "支付成功，结算失败。" + String.valueOf(mapXjResult.get(XjgjAccApiConstant.FIELD_MESSAGE)), mapBfResult);
                        }
                    } else {
                        trade.setState(SystemConstant.RECHARGE_STATE_PAY_OK);//支付成功但结算失败(宝付交易成功，西郊结算系统处理失败)
                        tradeLogService.saveTradeLog(trade);
                        return new ResultData("err_bf1xj0", false, "支付成功，结算失败。" + MsgConstant.MSG_REMOTE_ERROR, mapBfResult);
                    }
                } else {
                    trade.setAmtMoney(txnAmt);
                    trade.setState(SystemConstant.RECHARGE_STATE_FAILED);//支付失败且不结算(宝付圈存交易失败，则不执行西郊结算系统的圈存交易)
                    tradeLogService.saveTradeLog(trade);

                    String error = "圈存交易失败！" + String.valueOf(mapBfResult.get(BaofooApiConstant.FIELD_RESP_MSG));
                    logger.info(error);
                    return new ResultData("err_bf0xj0", false, error, mapBfResult);
                }
            } else {
                trade.setAmtMoney(txnAmt);
                trade.setState(SystemConstant.RECHARGE_STATE_FAILED);//支付失败且不结算
                tradeLogService.saveTradeLog(trade);

                String error = "圈存交易失败！支付服务暂时无法访问。";
                logger.info(error);
                Map<String, Object> rData = new HashMap<>();
                rData.put(XjgjAccApiConstant.FIELD_OLDREQUEST_NO, trade.getSellerOrderId());
                return new ResultData("err_bf0xj0", false, error, rData);
            }
        } catch (Exception e) {
            logger.error(MsgConstant.MSG_SERVER_ERROR, e);
            return new ResultData("err_exception", false, MsgConstant.MSG_SERVER_ERROR);
        }
    }

    /**
     * 查询宝付圈存交易结果
     *
     * @param params
     * @return
     */
    @RequestMapping("/resultBfRecharge")
    public ResultData resultBfRecharge(@RequestBody Map<String, String> params) {
        try {
            //验证token
            if (!checkAccessToken(params.get(SystemConstant.ACCESS_TOKEN))) {
                return new ResultData(MsgConstant.MSG_ERR_ACCESS_TOKEN_CODE, false, MsgConstant.MSG_ERR_ACCESS_TOKEN);
            }
            //宝付交易状态查询类交易
            params.put(BaofooApiConstant.FIELD_TXN_SUB_TYPE, BaofooApiConstant.TradeType.selectTradeState.getValue());
            String orig_trans_id = params.get(BaofooApiConstant.FIELD_ORIG_TRANS_ID);
            if (null == orig_trans_id || "".equals(orig_trans_id)) {
                return new ResultData("err_orig_trans_id_isnull", false, "原始商户订单号不能为空！");
            }
            String orig_trade_date = params.get(BaofooApiConstant.FIELD_ORIG_TRADE_DATE);
            if (null == orig_trade_date || "".equals(orig_trade_date)) {
                return new ResultData("err_orig_trade_date_isnull", false, "原交易订单时间不能为空！");
            }
            params.put(BaofooApiConstant.FIELD_TRANS_SERIAL_NO, OrderNumberUtils.generateInTime());

            Map<String, Object> mapBfResult = bfService.backTrans(params);
            if (mapBfResult != null) {
                logger.info("查询宝付圈存交易结果：" + JacksonUtils.beanToJson(mapBfResult));
                return new ResultData("ok", true, "", mapBfResult);
            } else {
                return new ResultData("err_remote_baofoo", false, "查询圈存支付结果失败。");
            }
        } catch (Exception e) {
            logger.error(MsgConstant.MSG_SERVER_ERROR, e);
            return new ResultData("err", false, MsgConstant.MSG_SERVER_ERROR);
        }
    }

    /**
     * 更新支付圈存交易状态，并进行(西郊)结算
     *
     * @param params
     * @return
     */
    @RequestMapping("/updateBfRechargeSuccess")
    public ResultData updateBfRechargeSuccess(@RequestBody Map<String, Object> params) {
        try {
            //验证token
            if (!checkAccessToken(String.valueOf(params.get(SystemConstant.ACCESS_TOKEN)))) {
                return new ResultData(MsgConstant.MSG_ERR_ACCESS_TOKEN_CODE, false, MsgConstant.MSG_ERR_ACCESS_TOKEN);
            }
            Object orig_trans_id = params.get(XjgjAccApiConstant.FIELD_OLDREQUEST_NO);
            if (null == orig_trans_id || "".equals(orig_trans_id)) {
                return new ResultData("err_orig_trans_id_isnull", false, "原始商户订单号不能为空！");
            }
            TradeLogEntity trade = new TradeLogEntity();
            trade.setSellerOrderId(String.valueOf(orig_trans_id));//根据商户订单号更新

            //调用西郊结算系统接口记录圈存交易
            params.put(XjgjAccApiConstant.FIELD_REQUEST_NO, OrderNumberUtils.generateInMillis());
            Map<String, Object> mapXjResult = xjgjService.recharge(params);
            if (mapXjResult != null) {
                if ("1".equals(mapXjResult.get(XjgjAccApiConstant.FIELD_RESULT))) {//西郊结算返回成功消息
                    trade.setState(SystemConstant.RECHARGE_STATE_SUCCESS);//支付成功且结算成功
                    tradeLogService.updateTradeLog(trade);

                    logger.info("已更新宝付圈存交易状态为成功，西郊国际结算处理圈存成功！商户订单号：" + String.valueOf(orig_trans_id) + "。");
                    return new ResultData("ok", true, MsgConstant.MSG_OPERATION_SUCCESS, mapXjResult);
                } else {
                    trade.setState(SystemConstant.RECHARGE_STATE_PAY_OK);//支付成功但结算失败(宝付交易成功，西郊结算系统处理失败)
                    tradeLogService.updateTradeLog(trade);

                    logger.info("已更新宝付圈存交易状态为成功，西郊国际结算处理圈存失败！商户订单号：" + String.valueOf(orig_trans_id) + "。");
                    return new ResultData("err_xj", false, "支付成功，结算处理失败。" + String.valueOf(mapXjResult.get(XjgjAccApiConstant.FIELD_MESSAGE)), mapXjResult);
                }
            } else {
                trade.setState(SystemConstant.RECHARGE_STATE_PAY_OK);
                tradeLogService.updateTradeLog(trade);
                logger.info("已更新宝付圈存交易状态为成功，西郊国际结算处理圈存失败！商户订单号：" + String.valueOf(orig_trans_id) + "。");
                return new ResultData("err_remote_xj", false, "支付成功，结算处理失败。");
            }
        } catch (Exception e) {
            logger.error(MsgConstant.MSG_SERVER_ERROR, e);
            return new ResultData("err_exception", false, MsgConstant.MSG_SERVER_ERROR);
        }
    }

    /**
     * 西郊结算会员圈存(充值)失败重试
     *
     * @param params
     * @return 返回结果
     */
    @RequestMapping("/retryXjRecharge")
    public ResultData retryXjRecharge(@RequestBody Map<String, Object> params) {
        try {
            //验证token
            if (!checkAccessToken(String.valueOf(params.get(SystemConstant.ACCESS_TOKEN)))) {
                return new ResultData(MsgConstant.MSG_ERR_ACCESS_TOKEN_CODE, false, MsgConstant.MSG_ERR_ACCESS_TOKEN);
            }
            Object trans_id = params.get(XjgjAccApiConstant.FIELD_OLDREQUEST_NO);//原圈存交易流水号
            if (null == trans_id || "".equals(trans_id)) {
                return new ResultData("err_trans_id_isnull", false, "原圈存请求编号不能为空！");
            }
            params.put(XjgjAccApiConstant.FIELD_REQUEST_NO, OrderNumberUtils.generateInTime());

            Map<String, Object> mapResult = xjgjService.retryRecharge(params);
            if ("1".equals(mapResult.get(XjgjAccApiConstant.FIELD_RESULT))) {//西郊结算返回成功消息
                //重试成功后更新上次失败的交易记录为成功
                TradeLogEntity trade = new TradeLogEntity();
                trade.setSellerOrderId(String.valueOf(trans_id));
                trade.setState(1);//宝付交易处理成功且西郊结算交易处理成功
                tradeLogService.updateTradeLog(trade);
                return new ResultData("ok", true, MsgConstant.MSG_OPERATION_SUCCESS, mapResult);
            } else {
                logger.info("西郊国际结算系统重试圈存交易处理失败！交易流水号："
                        + String.valueOf(trans_id) + "。");
                return new ResultData("err_xj", false, "结算重试圈存交易处理失败。" + String.valueOf(mapResult.get(XjgjAccApiConstant.FIELD_MESSAGE)), mapResult);
            }
        } catch (Exception e) {
            logger.error(MsgConstant.MSG_SERVER_ERROR, e);
            return new ResultData("err_exception", false, MsgConstant.MSG_SERVER_ERROR);
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
            //验证token
            if (!checkAccessToken(String.valueOf(params.get(SystemConstant.ACCESS_TOKEN)))) {
                return new ResultData(MsgConstant.MSG_ERR_ACCESS_TOKEN_CODE, false, MsgConstant.MSG_ERR_ACCESS_TOKEN);
            }

            if (params != null) {
                Object memberName = params.get(XjgjAccApiConstant.FIELD_MEMBER_NAME);//会员姓名
                if (null == memberName || "".equals(memberName)) {
                    return new ResultData("err_memberName_isnull", false, "会员姓名不能为空");
                }
                Object memberNo = params.get(XjgjAccApiConstant.FIELD_MEMBER_NO);//会员姓名
                if (null == memberNo || "".equals(memberNo)) {
                    return new ResultData("err_memberNo_isnull", false, "会员账号不能为空");
                }
                Object bankName = params.get(XjgjAccApiConstant.FIELD_BANK_NAME);//开户姓名
                if (null == bankName || "".equals(bankName)) {
                    return new ResultData("err_bankName_isnull", false, "开户姓名不能为空");
                }
                Object idCard = params.get(XjgjAccApiConstant.FIELD_ID_CARD);//身份证号
                if (null == idCard || "".equals(idCard)) {
                    return new ResultData("err_idCard_isnull", false, "开户身份证号不能为空");
                }
                Object bankAccountNo = params.get(XjgjAccApiConstant.FIELD_BANK_ACCOUNT);//手机号
                if (null == bankAccountNo || "".equals(bankAccountNo)) {
                    return new ResultData("err_bankAccountNo_isnull", false, "中国银行卡号不能为空");
                }
                Object pass = params.get(XjgjAccApiConstant.FIELD_PASSWORD);//会员密码
                if (null == pass || "".equals(pass)) {
                    return new ResultData("err_password_isnull", false, "会员密码不能为空！");
                } else if (!Pattern.matches("^[0-9]{6}", String.valueOf(pass))) {
                    return new ResultData("err_password_pattern", false, "主卡密码须是6位数字组成");
                }
                Map<String, Object> mapResult = xjgjService.memberBindBOC(params);
                if (mapResult != null) {
                    if ("1".equals(mapResult.get(XjgjAccApiConstant.FIELD_RESULT))) {
                        //圈提绑定信息保存到数据库
                        MemberBankcardEntity mBankcardInfo = memberBankService.getBankcardByBankCardID(String.valueOf(bankAccountNo));
                        if (mBankcardInfo != null) {
                            mBankcardInfo.setGmtModified(new Date());
                            memberBankService.updateMemberBankcard(mBankcardInfo);
                            return new ResultData("OK", true, MsgConstant.MSG_OPERATION_SUCCESS, "");
                        } else {
                            MemberInfoEntity memberInfo = memberInfoService.getMemberInfoByNO(String.valueOf(memberNo));
                            if (memberInfo != null) {
                                mBankcardInfo = new MemberBankcardEntity();
                                mBankcardInfo.setGmtModified(new Date());
                                mBankcardInfo.setIsRecharge(0);//新增的绑定圈提银行卡默认不能圈存，只能圈提，圈存需要再做圈存绑定后更新这个属性。
                                mBankcardInfo.setIsWithdraw(1);//可提现
                                mBankcardInfo.setBankAccCard(params.get(XjgjAccApiConstant.FIELD_BANK_ACCOUNT).toString());
                                mBankcardInfo.setBankAccName(params.get(XjgjAccApiConstant.FIELD_BANK_NAME).toString());
                                mBankcardInfo.setBankCode("BOC");
                                mBankcardInfo.setMemberInfoId(memberInfo.getId());
                                memberBankService.saveMemberBankcard(mBankcardInfo);
                                logger.info("本地数据库查询信息" + mBankcardInfo);
                                return new ResultData("ok", true, MsgConstant.MSG_OPERATION_SUCCESS, mapResult);
                            } else {
                                return new ResultData("ok", true, "本地数据库缺少信息", "");
                            }
                        }
                    } else {
                        return new ResultData("err", false, mapResult.get("message").toString(), "");
                    }

                } else {
                    return new ResultData("err", false, MsgConstant.MSG_OPERATION_FAILED, "");
                }
            } else {
                return new ResultData("err_no_params", false, "请求参数不能为空！", "");
            }
        } catch (Exception e) {
            logger.error(MsgConstant.MSG_SERVER_ERROR, e);
            return new ResultData("err", false, MsgConstant.MSG_SERVER_ERROR);
        }
    }

    /**
     * 会员圈提解绑
     *
     * @param params
     * @return
     */
    @RequestMapping("/memberUnBindBOC")
    public ResultData memberUnBindBOC(@RequestBody Map<String, Object> params) {
        try {
            //验证token
            if (!checkAccessToken(String.valueOf(params.get(SystemConstant.ACCESS_TOKEN)))) {
                return new ResultData(MsgConstant.MSG_ERR_ACCESS_TOKEN_CODE, false, MsgConstant.MSG_ERR_ACCESS_TOKEN);
            }

            Map<String, Object> mapResult = xjgjService.memberUnBindBOC(params);
            if (mapResult != null) {
                if ("1".equals(mapResult.get(XjgjAccApiConstant.FIELD_RESULT))) {
                    MemberBankcardEntity memberBankcardInfo = memberBankService.getBankcardByBankCardID(mapResult.get(XjgjAccApiConstant.FIELD_BANK_ACCOUNT).toString());
                    if (memberBankcardInfo != null) {
                        //更新数据库该银行卡的信息
                        memberBankService.updateWithdrawMemberBankCardInfo(memberBankcardInfo.getId());
                        return new ResultData("ok", true, "解除绑定关系成功", mapResult);
                    } else {
                        logger.info("本地数据库查询信息" + memberBankcardInfo);
                        return new ResultData("ok", true, "解除绑定关系成功");
                    }
                } else {
                    return new ResultData("err", false, mapResult.get("message").toString(), "");
                }
            } else {
                return new ResultData("err", false, MsgConstant.MSG_OPERATION_FAILED, "");
            }
        } catch (Exception e) {
            logger.error(MsgConstant.MSG_SERVER_ERROR, e);
            return new ResultData("err", false, MsgConstant.MSG_SERVER_ERROR);
        }
    }

    /**
     * 会员圈提
     *
     * @param Params
     * @return
     */
    @RequestMapping("/memberWithDraw")
    public ResultData memberWithDraw(@RequestBody Map<String, Object> Params) {
        try {
            //验证token
            if (!checkAccessToken(String.valueOf(Params.get(SystemConstant.ACCESS_TOKEN)))) {
                return new ResultData(MsgConstant.MSG_ERR_ACCESS_TOKEN_CODE, false, MsgConstant.MSG_ERR_ACCESS_TOKEN);
            }

            if (Params != null) {
                Object drawMoneyQty = Params.get(XjgjAccApiConstant.FIELD_MONEY);//以元为单位的支付金额

                if (null == drawMoneyQty || "".equals(drawMoneyQty)) {
                    return new ResultData("err_moneyQty_isnull", false, "圈提金额不能为空！");
                }

                BigDecimal txn_amt_num = new BigDecimal(String.valueOf(drawMoneyQty)).multiply(BigDecimal.valueOf(100));//金额转换成分
                Params.put(XjgjAccApiConstant.FIELD_MONEY, String.valueOf(txn_amt_num.setScale(0)));//以分为单位的金额
                Params.put(XjgjAccApiConstant.FIELD_REQUEST_NO, OrderNumberUtils.generateInTime());
                Map<String, Object> newMap = new HashMap<>();
                newMap.put(XjgjAccApiConstant.FIELD_MEMBER_NO, Params.get(XjgjAccApiConstant.FIELD_MEMBER_NO).toString());
                Map<String, Object> momenyBalanceMap = xjgjService.searchMemberAccountBalance(newMap);//查询剩余金额
                Map<String, Object> map = xjgjService.memberWithDraw(Params);
                List<MemberBankcardEntity> listData = memberBankService.listMemberBankcard(Params, 1);//判断圈提是否绑定成功

                if (map != null) {
                    if ("1".equals(map.get(XjgjAccApiConstant.FIELD_RESULT))) {
                        if (listData.size() != 0) {
                            BigDecimal accountBalance = new BigDecimal(momenyBalanceMap.get(XjgjAccApiConstant.FIELD_ACCOUNT_QTY).toString());
                            BigDecimal drawMoney = new BigDecimal(String.valueOf(Params.get(XjgjAccApiConstant.FIELD_MONEY)));//圈提金额
                            if (drawMoney.compareTo(accountBalance) > 0) {
                                return new ResultData("err", false, "账户余额不足！", " ");
                            }
                            if (drawMoney.equals(BigDecimal.ZERO)) {
                                return new ResultData("err", false, "圈提金额不能为0！", " ");
                            }
                            //  圈提记录保存到交易记录数据库
                            TradeLogEntity trade = new TradeLogEntity();
                            trade.setTransSn(OrderNumberUtils.generateInTime());
                            trade.setTransType(SystemConstant.TradeType.WITHDRAW.getValue());
                            //trade.setSellerOrderId(OrderNumberUtils.generateInTime());
                            trade.setAmtMoney(drawMoney);
                            trade.setPayModeId(SystemConstant.PayMode.XJGJ.getValue());
                            trade.setBankAccName(listData.get(0).getBankAccName());
                            trade.setBankAccCard(listData.get(0).getBankAccCard());
                            trade.setBankCode(listData.get(0).getBankCode());
                            trade.setGmtCreate(new Date());
                            tradeLogService.saveTradeLog(trade);
                            return new ResultData("ok", true, "圈提成功", map);
                        } else {
                            return new ResultData("err_noBind", false, "圈提绑定中国银行银行卡信息错误，请核对！", "");
                        }
                    } else {
                        return new ResultData("err_search_failed", false, map.get("message").toString(), "");
                    }
                } else {
                    return new ResultData("err_no_response", false, "圈提响应超时", "");
                }
            } else {
                return new ResultData("err_params_isnull", false, "请求参数不能为空！", "");
            }
        } catch (Exception e) {
            logger.error(MsgConstant.MSG_OPERATION_FAILED, e);
            return new ResultData("e1", false, MsgConstant.MSG_OPERATION_FAILED + e.getMessage(), "");
        }
    }

    /**
     * 查询账户余额
     *
     * @param params
     * @reutn
     */
    @RequestMapping("/searchMemberAccountBalance")
    public ResultData searchMemberAccountBalance(@RequestBody Map<String, Object> params) {
        try {
            //验证token
            if (!checkAccessToken(String.valueOf(params.get(SystemConstant.ACCESS_TOKEN)))) {
                return new ResultData(MsgConstant.MSG_ERR_ACCESS_TOKEN_CODE, false, MsgConstant.MSG_ERR_ACCESS_TOKEN);
            }
            Map<String, Object> mapResult = xjgjService.searchMemberAccountBalance(params);
            if (mapResult != null) {
                if ("1".equals(mapResult.get(XjgjAccApiConstant.FIELD_RESULT))) {
                    return new ResultData("ok", true, MsgConstant.MSG_OPERATION_SUCCESS, mapResult);
                } else {
                    return new ResultData("err", true, mapResult.get("message").toString(), "");
                }
            } else {
                return new ResultData("err_no_response", false, "查询响应超时", "");
            }
        } catch (Exception e) {
            logger.error(MsgConstant.MSG_OPERATION_FAILED, e);
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
            //验证token
            if (!checkAccessToken(String.valueOf(map.get(SystemConstant.ACCESS_TOKEN)))) {
                return new ResultData(MsgConstant.MSG_ERR_ACCESS_TOKEN_CODE, false, MsgConstant.MSG_ERR_ACCESS_TOKEN);
            }
            Map<String, Object> mapResult = xjgjService.searchMemberCostLog(map);
            return new ResultData("ok", true, MsgConstant.MSG_OPERATION_SUCCESS, mapResult);
        } catch (Exception e) {
            logger.error(MsgConstant.MSG_SERVER_ERROR, e);
            return new ResultData("e1", false, MsgConstant.MSG_SERVER_ERROR);
        }
    }

    /**
     * 银行列表（供客户端选择的银行列表）
     *
     * @param params
     * @return
     */
    @RequestMapping("/listBank")
    public ResultData listBank(@RequestBody Map<String, Object> params) {
        return new ResultData("ok", true, MsgConstant.MSG_OPERATION_SUCCESS, dicBankService.listAll(params));
    }

    /**
     * 会员交易银行卡列表
     *
     * @param params
     * @return
     */
    @RequestMapping("/listMemberBankcard")
    public ResultData listMemberBankcard(@RequestBody Map<String, Object> params) {
        //验证token
        if (!checkAccessToken(String.valueOf(params.get(SystemConstant.ACCESS_TOKEN)))) {
            return new ResultData(MsgConstant.MSG_ERR_ACCESS_TOKEN_CODE, false, MsgConstant.MSG_ERR_ACCESS_TOKEN);
        }
        List<MemberBankcardEntity> listData = memberBankService.listMemberBankcard(params, 1000);
        return new ResultData("ok", true, MsgConstant.MSG_OPERATION_SUCCESS, listData);
    }


    /**
     * 是否存在符合条件的银行卡信息
     *
     * @param params
     * @return
     */
    @RequestMapping("/getFirstMemberBankcard")
    public ResultData getFirstMemberBankcard(@RequestBody Map<String, Object> params) {
        //验证token
        if (!checkAccessToken(String.valueOf(params.get(SystemConstant.ACCESS_TOKEN)))) {
            return new ResultData(MsgConstant.MSG_ERR_ACCESS_TOKEN_CODE, false, MsgConstant.MSG_ERR_ACCESS_TOKEN);
        }
        String yesMsg = "存在符合条件的银行卡信息";
        String noMsg = "不存在符合条件的银行卡信息";
        List<MemberBankcardEntity> listData = memberBankService.listMemberBankcard(params, 1);
        if (listData != null) {
            if (listData.size() > 0) {
                return new ResultData("yes", true, yesMsg, listData.get(0));
            } else {
                return new ResultData("no", true, noMsg);
            }
        } else {
            return new ResultData("no", true, noMsg);
        }
    }

    /**
     * 试算圈存手续费
     *
     * @param params
     * @return
     */
    @RequestMapping("/poundageRecharge")
    public ResultData poundageRecharge(@RequestBody Map<String, Object> params) {
        //验证token
        if (!checkAccessToken(String.valueOf(params.get(SystemConstant.ACCESS_TOKEN)))) {
            return new ResultData(MsgConstant.MSG_ERR_ACCESS_TOKEN_CODE, false, MsgConstant.MSG_ERR_ACCESS_TOKEN);
        }
        try {
            BigDecimal txnAmt = new BigDecimal(String.valueOf(params.get(XjgjAccApiConstant.FIELD_MONEY)));
            String bindId = String.valueOf(params.get(BaofooApiConstant.FIELD_BIND_ID));

            if (null != bindId || !"".equals(bindId)) {
                MemberBankcardEntity bankCard = memberBankService.getBankcardByBfBindID(bindId);
                if (bankCard != null) {
                    BigDecimal poundage = xjgjService.poundageRecharge(txnAmt, bankCard.getBankCode());
                    return new ResultData("ok", true, "手续费：" + poundage, poundage);
                }
            }
        } catch (Exception e) {
            logger.error("无法计算手续费。", e);
        }
        return new ResultData("err", false, "无法计算手续费。");
    }

}
