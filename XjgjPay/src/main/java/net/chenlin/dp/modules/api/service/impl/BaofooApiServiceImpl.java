package net.chenlin.dp.modules.api.service.impl;

import net.chenlin.dp.common.constant.BaofooApiConstant;
import net.chenlin.dp.modules.api.manager.BaofooApiManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("baofooApiService")
public class BaofooApiServiceImpl {

    @Autowired
    private BaofooApiManager apiManager;

    @Value("${myprop.baofoo.member-id}")
    private String member_id;

    @Value("${myprop.baofoo.terminal-id}")
    private String terminal_id;

    @Value("${myprop.baofoo.data-type}")
    private String data_type;

    @Value("${myprop.baofoo.version}")
    private String version;

    public Map<String, Object> backTrans(Map<String, String> map) throws Exception {
        if (map != null) {
            String txn_sub_type = map.get("txn_sub_type");
            if (!txn_sub_type.isEmpty()) {
                Map<String, String> param = new HashMap<>();
                param.put("version", "4.0.0.0");
                param.put("member_id", member_id);
                param.put("terminal_id", terminal_id);
                param.put("txn_type", BaofooApiConstant.BfTransType.backTransaction.getValue());
                param.put("txn_sub_type", txn_sub_type);
                param.put("data_type", data_type);

                //TODO 调用宝付接口
                return apiManager.backTrans(map);
            }
        }
        return null;
    }

}
