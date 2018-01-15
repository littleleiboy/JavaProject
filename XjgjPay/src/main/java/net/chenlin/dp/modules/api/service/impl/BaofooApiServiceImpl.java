package net.chenlin.dp.modules.api.service.impl;

import net.chenlin.dp.common.constant.BaofooApiConstant;
import net.chenlin.dp.common.constant.SystemConstant;
import net.chenlin.dp.common.utils.*;
import net.chenlin.dp.modules.api.manager.BaofooApiManager;
import net.chenlin.dp.modules.api.service.BaofooApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("baofooApiService")
public class BaofooApiServiceImpl implements BaofooApiService {

    private final static Logger logger = LoggerFactory.getLogger(BaofooApiServiceImpl.class);

    @Autowired
    private BaofooApiManager apiManager;

    @Value("${myprop.api.baofoo.version}")
    private String version;

    @Value("${myprop.api.baofoo.member-id}")
    private String member_id;

    @Value("${myprop.api.baofoo.terminal-id}")
    private String terminal_id;

    @Value("${myprop.api.baofoo.data-type}")
    private String data_type;

    @Value("${myprop.api.baofoo.biz-type}")
    private String biz_type;

    @Value("${myprop.api.baofoo.pfx-pwd}")
    private String pfx_pwd;

    @Value("${myprop.api.baofoo.pfx-name}")
    private String pfx_name;

    @Value("${myprop.api.baofoo.cer-name}")
    private String cer_name;

    /**
     * 宝付支付接口调用
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> backTrans(Map<String, String> map) throws Exception {
        if (map != null) {
            String txn_sub_type = map.get(BaofooApiConstant.FIELD_TXN_SUB_TYPE);

            Map<String, String> param = new HashMap<>();
            param.put(BaofooApiConstant.FIELD_VERSION, version);
            param.put(BaofooApiConstant.FIELD_MEMBER_ID, member_id);
            param.put(BaofooApiConstant.FIELD_TERMINAL_ID, terminal_id);
            param.put(BaofooApiConstant.FIELD_TXN_TYPE, BaofooApiConstant.TradeType.backTransaction.getValue());
            param.put(BaofooApiConstant.FIELD_TXN_SUB_TYPE, txn_sub_type);
            param.put(BaofooApiConstant.FIELD_DATA_TYPE, data_type);

            //String trade_date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());//交易日期
            Map<String, Object> data = new HashMap<>();

            data.put(BaofooApiConstant.FIELD_VERSION, version);
            data.put(BaofooApiConstant.FIELD_TXN_SUB_TYPE, txn_sub_type);
            data.put(BaofooApiConstant.FIELD_BIZ_TYPE, biz_type);
            data.put(BaofooApiConstant.FIELD_TERMINAL_ID, terminal_id);
            data.put(BaofooApiConstant.FIELD_MEMBER_ID, member_id);
            //data.put(BaofooApiConstant.FIELD_TRANS_SERIAL_NO, "TISN" + System.currentTimeMillis());
            data.put(BaofooApiConstant.FIELD_TRANS_SERIAL_NO, map.get(BaofooApiConstant.FIELD_TRANS_SERIAL_NO));
            //
            data.put(BaofooApiConstant.FIELD_TXN_TYPE, BaofooApiConstant.TradeType.backTransaction.getValue());

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            data.put(BaofooApiConstant.FIELD_TRADE_DATE, formatter.format(new Date()));//订单日期

            data.put(BaofooApiConstant.FIELD_ADDITIONAL_INFO, map.get(BaofooApiConstant.FIELD_ADDITIONAL_INFO));
            data.put(BaofooApiConstant.FIELD_REQ_RESERVED, map.get(BaofooApiConstant.FIELD_REQ_RESERVED));

            switch (BaofooApiConstant.TradeType.getType(txn_sub_type)) {
                case prepareBinding:
                    data.put(BaofooApiConstant.FIELD_ACC_NO, map.get(BaofooApiConstant.FIELD_ACC_NO));//绑定卡号
                    data.put(BaofooApiConstant.FIELD_TRANS_ID, map.get(BaofooApiConstant.FIELD_TRANS_ID));//商户订单号
                    data.put(BaofooApiConstant.FIELD_ID_CARD_TYPE, map.get(BaofooApiConstant.FIELD_ID_CARD_TYPE));//证件类型固定01（身份证）
                    data.put(BaofooApiConstant.FIELD_ID_CARD, map.get(BaofooApiConstant.FIELD_ID_CARD));//身份证号
                    data.put(BaofooApiConstant.FIELD_ID_HOLDER, map.get(BaofooApiConstant.FIELD_ID_HOLDER));//持卡人姓名
                    data.put(BaofooApiConstant.FIELD_MOBILE, map.get(BaofooApiConstant.FIELD_MOBILE));//银行卡绑定手机号
                    //data.put(BaofooApiConstant.FIELD_ACC_PWD, map.get(BaofooApiConstant.FIELD_ACC_PWD));
                    data.put(BaofooApiConstant.FIELD_VALID_DATE, map.get(BaofooApiConstant.FIELD_VALID_DATE));//卡有效期
                    data.put(BaofooApiConstant.FIELD_VALID_NO, map.get(BaofooApiConstant.FIELD_VALID_NO));//卡安全码(银行卡背后最后三位数字)
                    data.put(BaofooApiConstant.FIELD_PAY_CODE, map.get(BaofooApiConstant.FIELD_PAY_CODE));//银行卡编码
                    break;
                case confirmBinding:
                    data.put(BaofooApiConstant.FIELD_SMS_CODE, map.get(BaofooApiConstant.FIELD_SMS_CODE));////短信验证码
                    data.put(BaofooApiConstant.FIELD_TRANS_ID, map.get(BaofooApiConstant.FIELD_TRANS_ID));//商户订单号
                    break;
                case cancelBinding:
                    data.put(BaofooApiConstant.FIELD_BIND_ID, map.get(BaofooApiConstant.FIELD_BIND_ID));//获取绑定标识
                    break;
                case selectBinding:
                    data.put(BaofooApiConstant.FIELD_ACC_NO, map.get(BaofooApiConstant.FIELD_ACC_NO));//卡号
                    break;
                case preparePay:
                    BigDecimal txn_amt_num = new BigDecimal(map.get(BaofooApiConstant.FIELD_TXN_AMT)).multiply(BigDecimal.valueOf(100));//金额转换成分
                    String txn_amt = String.valueOf(txn_amt_num.setScale(0));//支付金额
                    data.put(BaofooApiConstant.FIELD_TXN_AMT, txn_amt);//金额以分为单位(整型数据)并把元转换成分

                    data.put(BaofooApiConstant.FIELD_BIND_ID, map.get(BaofooApiConstant.FIELD_BIND_ID));
                    data.put(BaofooApiConstant.FIELD_TRANS_ID, map.get(BaofooApiConstant.FIELD_TRANS_ID));//商户订单号

                    Map<String, String> riskContent = new HashMap<>();
                    riskContent.put(BaofooApiConstant.FIELD_CLIENT_IP, map.get(BaofooApiConstant.FIELD_CLIENT_IP));
                    data.put(BaofooApiConstant.FIELD_RISK_CONTENT, riskContent);
                    break;
                case confirmPay:
                    data.put(BaofooApiConstant.FIELD_SMS_CODE, map.get(BaofooApiConstant.FIELD_SMS_CODE));//支付短信验证码
                    data.put(BaofooApiConstant.FIELD_BUSINESS_NO, map.get(BaofooApiConstant.FIELD_BUSINESS_NO));//宝付业务流水号
                    break;
                case selectTradeState:
                    data.put(BaofooApiConstant.FIELD_ORIG_TRANS_ID, map.get(BaofooApiConstant.FIELD_ORIG_TRANS_ID));
                    break;
            }

            /*Map<Object, Object> dataContent = new HashMap<>();
            dataContent.put(BaofooApiConstant.FIELD_DATA_CONTENT, data);*/

            String jsonOrXml ;
            if (BaofooApiConstant.DataType.JSON.getValue().equalsIgnoreCase(data_type)) {
                jsonOrXml = JacksonUtils.beanToJson(data);
            } else {
                jsonOrXml = JacksonUtils.beanToXml(data);
            }

            String path = CommonUtils.getClassRoot() + "\\" + SystemConstant.KEY_FILE_ROOT + "\\";
            String pfxPath = path + pfx_name;//商户私钥
            String cerPath = path + cer_name;//宝付公钥

            String base64str = EncryptUtils.Base64Encode(jsonOrXml);
            String data_content = RSAUtils.encryptByPriPfxFile(base64str, pfxPath, pfx_pwd);
            param.put(BaofooApiConstant.FIELD_DATA_CONTENT, data_content);

            //请求宝付接口方法
            String r = apiManager.backTrans(param);
            if (!r.isEmpty()) {
                r = RSAUtils.decryptByPubCerFile(r, cerPath);
                if (r.isEmpty()) {//判断解密是否正确。如果为空则宝付公钥不正确
                    logger.info("宝付解密公钥不正确！");
                    return null;
                }
                r = EncryptUtils.Base64Decode(r);

                if (BaofooApiConstant.DataType.XML.getValue().equalsIgnoreCase(map.get(BaofooApiConstant.FIELD_DATA_TYPE))) {
                    return JacksonUtils.xmlToMap(r);
                } else {
                    return JacksonUtils.jsonToMap(r);
                }
            } else {
                logger.info("宝付接口访问出错。");
                return null;
            }
        } else {
            logger.info("请求参数为空。");
            return null;
        }
    }

}
