package net.chenlin.dp.common.constant;

/**
 * 宝付接口静态资源
 */
public class BaofooApiConstant {

    /**
     * 接口版本号
     */
    public static final String FIELD_VERSION = "version";

    /**
     * 交易类型
     */
    public static final String FIELD_TXN_TYPE = "txn_type";

    /**
     * 交易子类型
     */
    public static final String FIELD_TXN_SUB_TYPE = "txn_sub_type";

    /**
     * 商户号
     */
    public static final String FIELD_MEMBER_ID = "member_id";

    /**
     * 终端号
     */
    public static final String FIELD_TERMINAL_ID = "terminal_id";

    /**
     * 数据类型json/xml
     */
    public static final String FIELD_DATA_TYPE = "data_type";

    /**
     * 银行编码
     */
    public static final String FIELD_PAY_CODE = "pay_code";

    /**
     * 绑定卡号
     */
    public static final String FIELD_ACC_NO = "acc_no";

    /**
     * 身份证号
     */
    public static final String FIELD_ID_CARD = "id_card";

    /**
     * 证件类型（01-身份证）
     */
    public static final String FIELD_ID_CARD_TYPE = "id_card_type";

    /**
     * 持卡人姓名
     */
    public static final String FIELD_ID_HOLDER = "id_holder";

    /**
     * 银行卡绑定手机号
     */
    public static final String FIELD_MOBILE = "mobile";

    /**
     * 商户订单号
     */
    public static final String FIELD_TRANS_ID = "trans_id";

    /**
     * 接入类型
     */
    public static final String FIELD_BIZ_TYPE = "biz_type";

    /**
     * 商户流水号
     */
    public static final String FIELD_TRANS_SERIAL_NO = "trans_serial_no";

    /**
     * 订单日期(14 位定长。格式：年年年年月月日日时时分分秒秒)
     */
    public static final String FIELD_TRADE_DATE = "trade_date";

    /**
     * 附加信息
     */
    public static final String FIELD_ADDITIONAL_INFO = "additional_info";

    /**
     * 请求方保留域
     */
    public static final String FIELD_REQ_RESERVED = "req_reserved";

    /**
     * 访问密码
     */
    public static final String FIELD_ACC_PWD = "acc_pwd";

    /**
     * 卡有效期
     */
    public static final String FIELD_VALID_DATE = "valid_date";

    /**
     * 卡安全码(银行卡背后最后三位数字)
     */
    public static final String FIELD_VALID_NO = "valid_no";

    /**
     * 绑定标识号
     */
    public static final String FIELD_BIND_ID = "bind_id";

    /**
     * 风险控制参数
     */
    public static final String FIELD_RISK_CONTENT = "risk_content";

    /**
     * 交易金额(金额以分为单位(整型数据)并把元转换成分)
     */
    public static final String FIELD_TXN_AMT = "txn_amt";

    /**
     * 短信验证码
     */
    public static final String FIELD_SMS_CODE = "sms_code";

    /**
     * 宝付业务流水号
     */
    public static final String FIELD_BUSINESS_NO = "business_no";

    /**
     * 原始商户订单号
     */
    public static final String FIELD_ORIG_TRANS_ID = "orig_trans_id";

    /**
     * 应答码
     */
    public static final String FIELD_RESP_CODE = "resp_code";

    /**
     * 应答信息
     */
    public static final String FIELD_RESP_MSG = "resp_msg";

    /**
     * 成功金额
     */
    public static final String FIELD_SUCC_AMT = "succ_amt";

    /**
     * 原交易订单日期
     */
    public static final String FIELD_ORIG_TRADE_DATE = "orig_trade_date";

    /**
     * 用户IP地址
     */
    public static final String FIELD_CLIENT_IP = "client_ip";

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
    public static final String FIELD_ORDER_STAT = "order_stat";

    /**
     * 加密数据
     */
    public static final String FIELD_DATA_CONTENT = "data_content";

    /**
     * 应答码-成功
     */
    public static final String RESP_CODE_SUCCESS = "0000";

    /**
     * 商户订单号不能为空
     */
    public static final String MSG_REQUIRE_TRANS_ID = "商户订单号不能为空。";

    /**
     * 短信验证码不能为空
     */
    public static final String MSG_REQUIRE_SMS_CODE = "短信验证码不能为空。";

    /**
     * 宝付交易类型
     */
    public enum TradeType {

        /**
         * 后台交易
         */
        backTransaction("0431"),
        /**
         * 预绑卡交易
         */
        prepareBinding("11", TradeType.backTransaction),
        /**
         * 确认绑卡交易
         */
        confirmBinding("12", TradeType.backTransaction),
        /**
         * 解除绑卡交易
         */
        cancelBinding("02", TradeType.backTransaction),
        /**
         * 查询绑定关系类交易
         */
        selectBinding("03", TradeType.backTransaction),
        /**
         * 支付类预支付交易
         */
        preparePay("15", TradeType.backTransaction),
        /**
         * 支付类支付确认交易
         */
        confirmPay("16", TradeType.backTransaction),
        /**
         * 交易状态查询类交易
         */
        selectTradeState("06", TradeType.backTransaction);

        private String value;
        private TradeType parentType;

        TradeType(String value, TradeType parentType) {
            this.value = value;
            this.parentType = parentType;
        }

        TradeType(String value) {
            this.value = value;
            this.parentType = null;
        }

        public String getValue() {
            return value;
        }

        public TradeType getType() {
            return this;
        }

        public TradeType getParentType() {
            return parentType;
        }

        public static TradeType getType(String value) {
            for (TradeType t : values()) {
                if (t.getValue() == value) {
                    return t;
                }
            }
            return null;
        }
    }

    /**
     * 数据类型
     */
    public enum DataType {

        JSON("json"),
        XML("xml");

        private String value;

        DataType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
