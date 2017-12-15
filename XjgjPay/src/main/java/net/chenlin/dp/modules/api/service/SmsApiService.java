package net.chenlin.dp.modules.api.service;

/**
 * 短信接口
 */
public interface SmsApiService {

    /**
     * 发送短信验证码
     */
    String sendSmsCode(String mobileNum);

    /**
     * 验证短信验证码
     *
     * @param mobileNum
     * @param code
     * @return
     */
    Boolean checkSmsCode(String mobileNum, String code);
}
