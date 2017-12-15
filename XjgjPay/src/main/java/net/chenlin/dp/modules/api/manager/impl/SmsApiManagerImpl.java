package net.chenlin.dp.modules.api.manager.impl;

import net.chenlin.dp.modules.api.manager.SmsApiManager;
import org.springframework.stereotype.Component;

/**
 * 短信接口
 */
@Component("smsApiManager")
public class SmsApiManagerImpl implements SmsApiManager {

    @Override
    public String sendSms(String mobileNum, String msg) {
        //TODO 调用接口发送短信
        return "";
    }

}
