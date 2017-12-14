package net.chenlin.dp.modules.api.controller;

import net.chenlin.dp.common.entity.ResultData;
import net.chenlin.dp.modules.api.service.XjgjAccApiService;
import net.chenlin.dp.modules.sys.controller.AbstractController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/xjgjacc")
public class XjgjAccApiController extends AbstractController {

    private final static Logger logger = LoggerFactory.getLogger(XjgjAccApiController.class);

    @Autowired
    private XjgjAccApiService apiService;

    @RequestMapping("/getMemberBindingInfo")
    public ResultData getMemberBindingInfo(@RequestBody Map<String, Object> params) {
        ResultData result = new ResultData();
        try {
            Map<String, Object> mapResult = apiService.getMemberBindingInfo(params);
            //TODO 处理调用返回
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
