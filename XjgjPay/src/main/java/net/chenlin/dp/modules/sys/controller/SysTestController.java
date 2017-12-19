package net.chenlin.dp.modules.sys.controller;

import net.chenlin.dp.common.utils.DynamicTokenUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 *  测试代码，发布前请注释或删除，避免安全漏洞。
 */
@RestController
@RequestMapping("/sys/test")
public class SysTestController {

    /*@Value("${myprop.api.jsxt.app-key}")
    private String jsxtKey;

    @RequestMapping(value = "/gdt", method = RequestMethod.GET)
    public String getDynamicToken() {
        return DynamicTokenUtils.getTokenByTime(jsxtKey);
    }

    @RequestMapping(value = "/ldt",method = RequestMethod.GET)
    public List<String> listDynamicToken(){
        return DynamicTokenUtils.listTokenByTime(jsxtKey);
    }*/
}
