package net.chenlin.dp.modules.api.manager;

/**
 * 短信接口
 */
public interface SmsApiManager {
    String sendSms(String mobileNum, String msg);
}
