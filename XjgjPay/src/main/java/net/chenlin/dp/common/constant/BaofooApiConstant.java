package net.chenlin.dp.common.constant;

/**
 * 宝付接口静态资源
 */
public class BaofooApiConstant {

    /**
     * 接口版本号
     */
    private static final String FIELD_VERSION = "version";

    /**
     * 交易类型
     */
    private static final String FIELD_TXN_TYPE = "txn_type";

    /**
     * 交易子类型
     */
    private static final String FIELD_TXN_SUB_TYPE = "txn_sub_type";

    /**
     * 商户号
     */
    private static final String FIELD_MEMBER_ID = "member_id";

    /**
     * 终端号
     */
    private static final String FIELD_TERMINAL_ID = "terminal_id";

    /**
     * 数据类型json/xml
     */
    private static final String FIELD_DATA_TYPE = "data_type";

    /**
     * 银行编码
     */
    private static final String FIELD_PAY_CODE = "pay_code";

    /**
     * 绑定卡号
     */
    private static final String FIELD_ACC_NO = "acc_no";

    /**
     * 身份证号
     */
    private static final String FIELD_ID_CARD = "id_card";

    /**
     * 证件类型（01-身份证）
     */
    private static final String FIELD_ID_CARD_TYPE = "id_card_type";

    /**
     * 持卡人姓名
     */
    private static final String FIELD_ID_HOLDER = "id_holder";

    /**
     * 银行卡绑定手机号
     */
    private static final String FIELD_MOBILE = "mobile";

    /**
     * 商户订单号
     */
    private static final String FIELD_TRANS_ID = "trans_id";

    /**
     * 接入类型
     */
    private static final String FIELD_BIZ_TYPE = "biz_type";

    /**
     * 商户流水号
     */
    private static final String FIELD_TRANS_SERIAL_NO = "trans_serial_no";

    /**
     * 订单日期
     */
    private static final String FIELD_TRADE_DATE = "trade_date";

    /**
     * 附加信息
     */
    private static final String FIELD_ADDITIONAL_INFO = "additional_info";

    /**
     * 请求方保留域
     */
    private static final String FIELD_REQ_RESERVED = "req_reserved";

    /**
     * 访问密码
     */
    private static final String FIELD_ACC_PWD = "acc_pwd";

    /**
     * 卡有效期
     */
    private static final String FIELD_VALID_DATE = "valid_date";

    /**
     * 卡安全码(银行卡背后最后三位数字)
     */
    private static final String FIELD_VALID_NO = "valid_no";

    /**
     * 绑定标识号
     */
    private static final String FIELD_BIND_ID = "bind_id";

    /**
     * 风险控制参数
     */
    private static final String FIELD_RISK_CONTENT = "risk_content";

    /**
     * 交易金额(金额以分为单位(整型数据)并把元转换成分)
     */
    private static final String FIELD_TXN_AMT = "txn_amt";

    /**
     * 短信验证码
     */
    private static final String FIELD_SMS_CODE = "sms_code";

    /**
     * 宝付业务流水号
     */
    private static final String FIELD_BUSINESS_NO = "business_no";

    /**
     * 原始商户订单号
     */
    private static final String FIELD_ORIG_TRANS_ID = "orig_trans_id";

    /**
     * 应答码
     */
    private static final String FIELD_RESP_CODE = "resp_code";

    /**
     * 应答信息
     */
    private static final String FIELD_RESP_MSG = "resp_msg";

    /**
     * 成功金额
     */
    private static final String FIELD_SUCC_AMT = "succ_amt";

    /**
     * 原交易订单日期
     */
    private static final String FIELD_ORIG_TRADE_DATE = "orig_trade_date";

    /**
     * 交易结果状态码
     * (状态码取值(大写字母)：
     * S：交易成功
     * F：交易失败
     * I：处理中
     * FF:：交易失败
     * IMPORTANT：当状态码结果
     * 为 FF 时，非支付订单交易结
     * 果，如：必传参数缺失时，报
     * 文解析失败时，参数格式校验
     * 失败等。)
     */
    private static final String FIELD_ORDER_STAT = "order_stat";

    /**
     * 宝付交易类型
     */
    public enum BfTransType {

        /**
         * 后台交易
         */
        backTransaction("0431"),

        prepareBinding("11", BfTransType.backTransaction);

        private String value;
        private BfTransType parentType;

        BfTransType(String value, BfTransType parentType) {
            this.value = value;
            this.parentType = parentType;
        }

        BfTransType(String value) {
            this.value = value;
            this.parentType = null;
        }

        public String getValue() {
            return value;
        }

        public BfTransType getType() {
            return this;
        }

        public BfTransType getParentType() {
            return parentType;
        }
    }

}
