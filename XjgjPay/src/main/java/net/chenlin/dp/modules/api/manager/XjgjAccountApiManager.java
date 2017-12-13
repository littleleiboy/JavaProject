package net.chenlin.dp.modules.api.manager;

/**
 * 西郊国际结算系统API管理
 */
public interface XjgjAccountApiManager {

    /**
     * 通过手机号查询会员绑定基本信息
     *
     * @param str 发送数据字符串
     * @return 返回结果字符串
     */
    String getMemberBindingInfo(String str);

}
