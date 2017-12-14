package net.chenlin.dp.common.constant;

/**
 * 西郊国际结算系统接口资源
 */
public class XjgjAccApiConstant {

    /**
     * 方法名常量，通过手机号查询会员是否已经绑定。
     * 完整的请求url格式例如 https://mail.shxjgj.com:443/app/memberSearchRequest
     */
    public static final String METHOD_GET_MEMBER_BINDING_INFO = "memberSearchRequest";

    /**
     * 方法名常量，已注册会员绑定APP。
     */
    public static final String METHOD_MEMBER_APP_BIND = "memberBindRequest";

    public static final String FIELD_KEY_STR = "keyStr";
    public static final String FIELD_MESSAGE = "message";
    public static final String FIELD_RESULT = "result";
    public static final String FIELD_MOBILE_NUM = "mobileNum";
    public static final String FIELD_IS_MEMBER = "ifHasMember";
    public static final String FIELD_MEMBER_NO = "memberNo";
    public static final String FIELD_MEMBER_NAME = "memberName";
    public static final String FIELD_BIND_ID = "bind_id";
    public static final String FIELD_IS_QT_BINDED = "ifQtBinded";
    public static final String FIELD_ID_CARD = "idCardNo";
    public static final String FIELD_PASSWORD = "password";
    public static final String FIELD_BANK_ACCOUNT = "bankAccount";
    public static final String FIELD_M_CARD_NO = "mCardNo2";
    public static final String FIELD_TO_CORP_ADDRESS = "toCorpAddress";
    public static final String FIELD_BANK_NAME = "bankName";
    public static final String FIELD_ACCOUNT_QTY = "accountQty";
    public static final String FIELD_TOXJGJ_QTY = "toXJGJQty";
    public static final String FIELD_REQUEST_NO = "requestNo";
    public static final String FIELD_OLDREQUEST_NO = "oldRequestNo";
    public static final String FIELD_MONEY = "moneyQty";
    public static final String FIELD_START_DATE = "startDate";
    public static final String FIELD_END_DATE = "endDate";
    public static final String FIELD_COST_LOG = "costLog";

}
