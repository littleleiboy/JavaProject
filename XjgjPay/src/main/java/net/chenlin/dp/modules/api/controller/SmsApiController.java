package net.chenlin.dp.modules.api.controller;

import net.chenlin.dp.modules.api.service.SmsApiService;
import net.chenlin.dp.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sms")
public class SmsApiController extends AbstractController {

    @Autowired
    private SmsApiService smsService;

    @RequestMapping("/sendSmsCode")
    public String sendSmsCode(String mobileNum) {
        return smsService.sendSmsCode(mobileNum);
    }

    @RequestMapping("/checkSmsCode")
    public Boolean checkSmsCode(String mobileNum, String code) {
        return smsService.checkSmsCode(mobileNum, code);
    }

}
