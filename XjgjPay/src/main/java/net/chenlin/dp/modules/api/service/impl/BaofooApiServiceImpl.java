package net.chenlin.dp.modules.api.service.impl;

import net.chenlin.dp.common.constant.BaofooApiConstant;
import net.chenlin.dp.common.utils.JacksonUtils;
import net.chenlin.dp.common.utils.EncryptUtils;
import net.chenlin.dp.common.utils.RSAUtils;
import net.chenlin.dp.modules.api.manager.BaofooApiManager;
import net.chenlin.dp.modules.api.service.BaofooApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service("baofooApiService")
public class BaofooApiServiceImpl implements BaofooApiService {

    @Autowired
    private BaofooApiManager apiManager;

    @Value("${myprop.baofoo.version}")
    private String version;

    @Value("${myprop.baofoo.member-id}")
    private String member_id;

    @Value("${myprop.baofoo.terminal-id}")
    private String terminal_id;

    @Value("${myprop.baofoo.data-type}")
    private String data_type;

    @Value("${myprop.baofoo.biz-type}")
    private String biz_type;

    @Value("${myprop.baofoo.pfx-pwd}")
    private String pfx_pwd;

    /**
     * 宝付支付接口调用
     * @param pfxPath
     * @param cerPath
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> backTrans(String pfxPath, String cerPath, Map<String, String> map) throws Exception {
        if (map != null) {
            /*String pay_code = request.getParameter("pay_code");//银行卡编码
            String acc_no = request.getParameter("acc_no");//银行卡卡号
            String id_card = request.getParameter("id_card");//身份证号码
            String id_holder = request.getParameter("id_holder");//姓名
            String mobile = request.getParameter("mobile");//银行预留手机号
            String trans_id = request.getParameter("trans_id");	//商户订单号*/

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

            data.put(BaofooApiConstant.FIELD_TXN_SUB_TYPE, txn_sub_type);
            data.put(BaofooApiConstant.FIELD_BIZ_TYPE, biz_type);
            data.put(BaofooApiConstant.FIELD_TERMINAL_ID, terminal_id);
            data.put(BaofooApiConstant.FIELD_MEMBER_ID, member_id);
            //data.put(BaofooApiConstant.FIELD_TRANS_SERIAL_NO, "TISN" + System.currentTimeMillis());
            data.put(BaofooApiConstant.FIELD_TRANS_SERIAL_NO, map.get(BaofooApiConstant.FIELD_TRANS_SERIAL_NO));
            data.put(BaofooApiConstant.FIELD_TRADE_DATE, map.get(BaofooApiConstant.FIELD_TRADE_DATE));
            data.put(BaofooApiConstant.FIELD_ADDITIONAL_INFO, map.get(BaofooApiConstant.FIELD_ADDITIONAL_INFO));
            data.put(BaofooApiConstant.FIELD_REQ_RESERVED, map.get(BaofooApiConstant.FIELD_REQ_RESERVED));

            switch (BaofooApiConstant.TradeType.getType(txn_sub_type)) {
                case prepareBinding:
                    data.put(BaofooApiConstant.FIELD_ACC_NO, map.get(BaofooApiConstant.FIELD_ACC_NO));//绑定卡号
                    data.put(BaofooApiConstant.FIELD_TRANS_ID, map.get(BaofooApiConstant.FIELD_TRANS_ID));//商户订单号
                    data.put(BaofooApiConstant.FIELD_ID_CARD_TYPE, map.get(BaofooApiConstant.FIELD_ID_CARD_TYPE));//证件类型固定01（身份证）
                    data.put(BaofooApiConstant.FIELD_ID_CARD, map.get(BaofooApiConstant.FIELD_ID_CARD));
                    data.put(BaofooApiConstant.FIELD_ID_HOLDER, map.get(BaofooApiConstant.FIELD_ID_HOLDER));
                    data.put(BaofooApiConstant.FIELD_MOBILE, map.get(BaofooApiConstant.FIELD_MOBILE));
                    data.put(BaofooApiConstant.FIELD_ACC_PWD, map.get(BaofooApiConstant.FIELD_ACC_PWD));
                    data.put(BaofooApiConstant.FIELD_VALID_DATE, map.get(BaofooApiConstant.FIELD_VALID_DATE));
                    data.put(BaofooApiConstant.FIELD_VALID_NO, map.get(BaofooApiConstant.FIELD_VALID_NO));
                    data.put(BaofooApiConstant.FIELD_PAY_CODE, map.get(BaofooApiConstant.FIELD_PAY_CODE));
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

            Map<Object, Object> dataContent = new HashMap<>();
            dataContent.put(BaofooApiConstant.FIELD_DATA_CONTENT, data);

            String jsonOrXml;
            if (BaofooApiConstant.DataType.JSON.getValue().equalsIgnoreCase(data_type)) {
                jsonOrXml = JacksonUtils.beanToJson(dataContent);
            } else {
                jsonOrXml = JacksonUtils.beanToXml(dataContent);
            }

            //String pfxpath = BaofooAction.getWebRoot() + "CER\\" + BaofooAction.getConstants().get("pfx.name");//商户私钥
            //String cerpath = BaofooAction.getWebRoot()+"CER\\"+BaofooAction.getConstants().get("cer.name");//宝付公钥

            String base64str = EncryptUtils.Base64Encode(jsonOrXml);
            String data_content = RSAUtils.encryptByPriPfxFile(base64str, pfxPath, pfx_pwd);
            param.put("data_content", data_content);

            return apiManager.backTrans(map);
        }
        return null;
    }

}
