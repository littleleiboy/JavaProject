package net.chenlin.dp.modules.api.manager.impl;

import net.chenlin.dp.common.constant.BaofooApiConstant;
import net.chenlin.dp.common.constant.SystemConstant;
import net.chenlin.dp.common.utils.*;
import net.chenlin.dp.modules.api.manager.BaofooApiManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component("baofooApiManager")
public class BaofooApiManagerImpl implements BaofooApiManager {

    @Value("${myprop.api.baofoo.url}")
    private String apiUrl;

    /**
     * 宝付认证支付接口
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public String backTrans(Map<String, String> map) throws Exception {
        return HttpUtils.postIgnoreSSL(apiUrl, createLinkStringByGetKey(map));
    }
	
	/**
     * 将json格式参数转换成字符串类型，并用"&"拼接
     * 参数 （json格式）
     * 2018-01-15
    * */
     private static String createLinkStringByGetKey(Map<String, String> params) throws UnsupportedEncodingException {
        List<String> keys = new ArrayList< >(params.keySet());
        Collections.sort(keys);
        String returnstr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            value = URLEncoder.encode(value, "UTF-8");
            if (i == keys.size() - 1) { //拼接时，不包括最后一个&字符
                returnstr = returnstr + key + "=" + value;
            } else {
                returnstr = returnstr + key + "=" + value + "&";
            }
        }
        return returnstr;
     }
	
}
