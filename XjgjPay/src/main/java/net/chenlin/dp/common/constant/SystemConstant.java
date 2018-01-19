package net.chenlin.dp.common.constant;

/**
 * 系统级静态变量
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 * @url www.chenlintech.com
 * @date 2017年8月8日 下午1:35:38
 */
public class SystemConstant {

    /**
     * 超级管理员ID
     */
    public static final long SUPER_ADMIN = 1;

    /**
     * 数据标识
     */
    public static final String DATA_ROWS = "rows";

    /**
     * 未授权错误代码
     */
    public static final int UNAUTHORIZATION_CODE = 401;

    /**
     * 访问口令KEY
     */
    public static final String ACCESS_TOKEN = "access_token";

    /**
     * 秘钥文件目录名
     */
    public static final String KEY_FILE_ROOT = "cer";

    /**
     * APP会员登录后返回数据KEY：会员信息主键ID
     */
    public static final String MEMBER_PK = "member_pk";
    /**
     * APP会员登录后返回数据KEY：会员账号
     */
    public static final String MEMBER_NO = "member_no";
    /**
     * APP会员登录后返回数据KEY：会员名
     */
    public static final String MEMBER_NAME = "member_name";
    /**
     * APP会员登录后返回数据KEY：会员手机号
     */
    public static final String MEMBER_MOBILE = "member_mobile";
    /**
     * 圈存交易状态：支付成功，结算成功。
     */
    public static final int RECHARGE_STATE_SUCCESS = 1;
    /**
     * 圈存交易状态：支付失败，未结算。
     */
    public static final int RECHARGE_STATE_FAILED = 2;
    /**
     * 圈存交易状态：支付成功，结算失败。
     */
    public static final int RECHARGE_STATE_PAY_OK = 3;

    /**
     * 菜单类型
     *
     * @author ZhouChenglin
     * @email: yczclcn@163.com
     * @url: www.chenlintech.com
     * @date 2017年8月8日 下午1:36:27
     */
    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        private MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 定时任务状态
     *
     * @author ZhouChenglin
     * @email: yczclcn@163.com
     * @url: www.chenlintech.com
     * @date 2017年8月8日 下午1:36:17
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(1),
        /**
         * 暂停
         */
        PAUSE(0);

        private int value;

        private ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 通用字典
     *
     * @author ZhouChenglin
     * @email yczclcn@163.com
     * @url www.chenlintech.com
     * @date 2017年8月15日 下午7:29:02
     */
    public enum MacroType {

        /**
         * 类型
         */
        TYPE(0),

        /**
         * 参数
         */
        PARAM(1);

        private int value;

        private MacroType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }

    /**
     * 通用变量，表示可用、禁用、显示、隐藏
     *
     * @author ZhouChenglin
     * @email yczclcn@163.com
     * @url www.chenlintech.com
     * @date 2017年8月15日 下午7:31:49
     */
    public enum StatusType {

        /**
         * 禁用，隐藏
         */
        DISABLE(0),

        /**
         * 可用，显示
         */
        ENABLE(1),

        /**
         * 显示
         */
        SHOW(1),

        /**
         * 隐藏
         */
        HIDDEN(0);

        private int value;

        private StatusType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }

    /**
     * 交易类型
     *
     * @author Andy Wang
     * @date 2017-12-27
     */
    public enum TradeType {

        /**
         * 充值（圈存）
         */
        RECHARGE(1),
        /**
         * 提现（圈提）
         */
        WITHDRAW(2),
        /**
         * 消费
         */
        CONSUME(3),
        /**
         * 转账
         */
        TRANSFER(4),
        /**
         * 收款
         */
        COLLECT(5);

        private int value;

        TradeType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 支付方式
     */
    public enum PayMode {

        /**
         * 宝付支付系统
         */
        BAOFOO(1),
        /**
         * 西郊国际结算系统
         */
        XJGJ(2);

        private int value;

        PayMode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
