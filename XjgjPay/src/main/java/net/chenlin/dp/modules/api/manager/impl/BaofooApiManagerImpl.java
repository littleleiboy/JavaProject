package net.chenlin.dp.modules.api.manager.impl;

import net.chenlin.dp.common.constant.BaofooApiConstant;
import net.chenlin.dp.common.constant.SystemConstant;
import net.chenlin.dp.common.utils.*;
import net.chenlin.dp.modules.api.manager.BaofooApiManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("baofooApiManager")
public class BaofooApiManagerImpl implements BaofooApiManager {

    @Value("${myprop.api.baofoo.url}")
    private String apiUrl;

    @Value("${myprop.api.baofoo.cer-name}")
    private String cer_name;

    /**
     * 宝付认证支付接口
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> backTrans(Map<String, String> map) throws Exception {
        String r = HttpUtils.postRequestSSL(apiUrl, JacksonUtils.beanToJson(map));
        if (!r.isEmpty()) {
            String path = SpringContextUtils.getRealPath(SystemConstant.KEY_FILE_ROOT);
            String cerPath = path + "\\" + cer_name;//宝付公钥
            r = RSAUtils.decryptByPubCerFile(r, cerPath);
            r = EncryptUtils.Base64Decode(r);

            if (BaofooApiConstant.DataType.XML.getValue().equals(map.get(BaofooApiConstant.FIELD_DATA_TYPE))) {
                return JacksonUtils.xmlToMap(r);
            } else {
                return JacksonUtils.jsonToMap(r);
            }
        } else {
            return null;
        }
    }
}
