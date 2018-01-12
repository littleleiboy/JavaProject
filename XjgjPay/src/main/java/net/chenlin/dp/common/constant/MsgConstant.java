package net.chenlin.dp.common.constant;

/**
 * 系统提示静态变量
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 * @url www.chenlintech.com
 * @date 2017年8月12日 下午1:33:18
 */
public class MsgConstant {

    /**
     * 操作成功
     */
    public static final String MSG_OPERATION_SUCCESS = "操作成功！";

    /**
     * 操作失败
     */
    public static final String MSG_OPERATION_FAILED = "操作失败！";

    /**
     * 操作失败，远程服务出现错误
     */
    public static final String MSG_REMOTE_ERROR = "操作失败，远程服务出现错误！";

    /**
     * 操作失败，后台处理错误
     */
    public static final String MSG_SERVER_ERROR = "操作失败，后台处理错误！";

    /**
     * 删除时，提示有子节点无法删除
     */
    public static final String MSG_HAS_CHILD = "操作失败，当前所选数据有子节点数据！";

    /**
     * 加载表单数据错误提示
     */
    public static final String MSG_INIT_FORM = "初始化表单数据失败，请重试！";

    /**
     * 访问授权验证未通过
     */
    public static final String MSG_ERR_ACCESS_TOKEN = "访问授权验证未通过！";

    /**
     * 访问授权验证未通过错误代码：err_access_token
     */
    public static final String MSG_ERR_ACCESS_TOKEN_CODE = "err_access_token";

    /**
     * 是否已经绑定APP
     */
    public static final String MSG_IS_APP_BOUND = "is_app_bound";

    /**
     * 删除数据项不是全部所选
     *
     * @param total
     * @param process
     * @return
     */
    public static String removeFailed(int total, int process) {
        return "本次共处理：" + String.valueOf(total) + "条，成功：" + String.valueOf(process) + "条！";
    }

}
