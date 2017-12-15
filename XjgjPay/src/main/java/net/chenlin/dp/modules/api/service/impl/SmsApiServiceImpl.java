package net.chenlin.dp.modules.api.service.impl;

import net.chenlin.dp.common.utils.ShiroUtils;
import net.chenlin.dp.modules.api.manager.SmsApiManager;
import net.chenlin.dp.modules.api.service.SmsApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 短信接口
 */
@Service("smsApiService")
public class SmsApiServiceImpl implements SmsApiService {

    @Autowired
    private SmsApiManager apiManager;

    /**
     * 短信验证码缓存Key前缀（缓存Key以"SmsCode" + 手机号命名）
     */
    private static final String SMS_CODE_KEY_PREFIX = "SmsCode";

    /**
     * 发送短信验证码
     */
    @Override
    public String sendSmsCode(String mobileNum) {
        String code = String.valueOf((Math.random() * 9 + 1) * 100000)
                + "," + Calendar.getInstance().getTimeInMillis();
        ShiroUtils.setSessionAttribute(SMS_CODE_KEY_PREFIX + mobileNum, code);
        return apiManager.sendSms(mobileNum, code);
    }

    /**
     * 验证短信验证码
     *
     * @param mobileNum
     * @param code
     * @return
     */
    @Override
    public Boolean checkSmsCode(String mobileNum, String code) {
        Object val = ShiroUtils.getSessionAttribute(SMS_CODE_KEY_PREFIX + mobileNum);
        if (val != null) {
            String[] arr = String.valueOf(val).split(",");
            String strCode = arr[0];
            String strTime = arr[1];

            long saveTime = Long.valueOf(strTime);
            long checkTime = Calendar.getInstance().getTimeInMillis();
            if ((checkTime - saveTime) <= (1000 * 60 * 5)) {//验证码有效时间为5分钟
                if (code.equals(strCode)) {
                    return true;
                }
            }
        }
        ShiroUtils.removeSessionAttribute(SMS_CODE_KEY_PREFIX + mobileNum);
        return false;
    }

}
