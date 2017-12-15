package net.chenlin.dp.modules.api.controller;

import net.chenlin.dp.common.entity.ResultData;
import net.chenlin.dp.common.utils.JSONUtils;
import net.chenlin.dp.modules.api.service.XjgjAccApiService;
import net.chenlin.dp.modules.base.entity.MemberInfoEntity;
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


    @RequestMapping("/memberWithDraw")
    public ResultData memberWithDraw(@RequestBody Map<String,Object> params){
        Object result = null;
        String accountBalancekey = "accountQty";
        String drawKey = "moneyQty";
        try {
            Map<String,Object> map = apiService.memberWithDraw(params);
            Map<String,Object> momenyBalanceMap = apiService.searchMemberAccountBalance(params.get("memberNo").toString());
            result = JSONUtils.mapToBean(map,MemberInfoEntity.class);
            int accountBalance = (int)momenyBalanceMap.get(accountBalancekey);
            int drawMoney = (int)params.get(drawKey);
            if(accountBalance <= 0 && drawMoney > accountBalance){
                return new ResultData("err", false, "账户余额不足！", "");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResultData("ok",true,"查询成功",result);
    }

    @RequestMapping("/searchMemberAccountBalance")
    public ResultData searchMemberAccountBalance(@RequestBody String param){
        Object result = null;
        try {
            Map<String,Object> mapResult = apiService.searchMemberAccountBalance(param);
            result = JSONUtils.mapToBean(mapResult,MemberInfoEntity.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        return  new ResultData("ok",true,"查询成功",result);
    }

    @RequestMapping("/searchMemberAccountChange")
    public ResultData searchMemberAccountChangeByPeriodOfTime(Map<String,Object> map){
        Object result = null;
        try {
            Map<String,Object> mapResult = apiService.searchMemberCostLog(map);
            result = JSONUtils.mapToBean(mapResult,MemberInfoEntity.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        return  new ResultData("ok",true,"查询成功",result);
    }

}
