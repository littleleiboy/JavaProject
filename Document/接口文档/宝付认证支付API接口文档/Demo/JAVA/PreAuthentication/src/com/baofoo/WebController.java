package com.baofoo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import java.math.BigDecimal;

import com.baofoo.rsa.RsaCodingUtil;
import com.baofoo.util.HttpUtil;
import com.baofoo.util.JXMConvertUtil;
import com.baofoo.util.MapToXMLString;
import com.baofoo.util.SecurityUtil;
/**
 * 
 * API宝付认证4.0.0.0
 * 本实例依赖包在WEB-IF/lib文件夹内，配置文件在System_Config/app.properties
 * 实例仅供学习<宝付认证API 4.0.0.0>接口使用，仅供参考。商户可根据本实例写自已的代码
 * 
 * 宝付测试专用账号  
 * 工商银行:6222020111122220000，农业银行:6228480444455553333 。
 * 测试环境说明：姓名、身份证号  自已输入，手机号必须为真实手机。
 * 
 * 注意：测试环境返回的“bind_id”有效期为2-5分钟内过期需要重新绑卡
 * @author：宝付（大圣）
 * 
 */
public class WebController {
	
	//请求方法
	public String Api(HttpServletRequest request,HttpServletResponse response) throws IOException{
        String txn_sub_type = request.getParameter("txn_sub_type");

        if (txn_sub_type.isEmpty())
        {
            return "参数【txn_sub_type】不能为空";
        }
        
        String pay_code = request.getParameter("pay_code");//银行卡编码
        String acc_no = request.getParameter("acc_no");//银行卡卡号
        String id_card = request.getParameter("id_card");//身份证号码
        String id_holder = request.getParameter("id_holder");//姓名
        String mobile = request.getParameter("mobile");//银行预留手机号
        String trans_id = request.getParameter("trans_id");	//商户订单号

		Map<String,String> HeadPostParam = new HashMap<String,String>();
        
        HeadPostParam.put("version", "4.0.0.0");
        HeadPostParam.put("member_id", BaofooAction.getConstants().get("member.id"));
        HeadPostParam.put("terminal_id", BaofooAction.getConstants().get("terminal.id"));
        HeadPostParam.put("txn_type", "0431");
        HeadPostParam.put("txn_sub_type", txn_sub_type);
        HeadPostParam.put("data_type", BaofooAction.getConstants().get("data.type"));
        
        String request_url = "https://vgw.baofoo.com/cutpayment/api/backTransRequest";//测试地址
        
        String  pfxpath = BaofooAction.getWebRoot()+"CER\\"+BaofooAction.getConstants().get("pfx.name");//商户私钥
        
        String  cerpath = BaofooAction.getWebRoot()+"CER\\"+BaofooAction.getConstants().get("cer.name");//宝付公钥
        
        /**
         * 报文体
         * =============================================
         * 
         */
        String trade_date=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());//交易日期        
        Map<String,Object> XMLArray = new HashMap<String,Object>();
        

        /**
         * 
         * 公共参数
         * 
         */
        XMLArray.put("txn_sub_type", HeadPostParam.get("txn_sub_type"));
        XMLArray.put("biz_type", "0000");
        XMLArray.put("terminal_id", HeadPostParam.get("terminal_id"));
        XMLArray.put("member_id", HeadPostParam.get("member_id"));
        XMLArray.put("trans_serial_no", "TISN"+System.currentTimeMillis());
        XMLArray.put("trade_date", trade_date);
        XMLArray.put("additional_info", "附加信息");
        XMLArray.put("req_reserved", "保留");

        if(txn_sub_type.equals("11")){
        	
        	log("========预绑卡类交易===========");
            if (trans_id.isEmpty())
            {
            	return "商户订单号不能为空【trans_id】";
            }
            XMLArray.put("acc_no", acc_no);//商户订单号
            XMLArray.put("trans_id", trans_id);//商户订单号
            XMLArray.put("id_card_type", "01");//证件类型固定01（身份证）
            XMLArray.put("id_card", id_card);
            XMLArray.put("id_holder", id_holder);
            XMLArray.put("mobile", mobile);
            XMLArray.put("acc_pwd", "");
            XMLArray.put("valid_date", "");
            XMLArray.put("valid_no", "");
            XMLArray.put("pay_code", pay_code);
            //
            
        }else if(txn_sub_type.equals("12")){
        	log("========确认绑卡交易===========");
            if (trans_id.isEmpty())
            {
            	return "商户订单号不能为空【trans_id】,注：需和预绑卡交易订单号一致";
            }
            
        	String sms_code = request.getParameter("sms_code");//短信验证码
            if(sms_code.isEmpty()){
                return "短信验证码不能为空【sms_code】";
            }
            XMLArray.put("sms_code", sms_code); 
            XMLArray.put("trans_id", trans_id);//商户订单号
            
        }else if(txn_sub_type.equals("02")){
        	
        	log("========解除绑卡交易===========");
            String bind_id = request.getParameter("bind_id");//获取绑定标识	 
            XMLArray.put("bind_id",bind_id);
            
        }else if(txn_sub_type.equals("03")){        
        	
        	log("========查询绑定关系类交易===========");
            XMLArray.put("acc_no",acc_no) ;//卡号
            
        }else if(txn_sub_type.equals("15")){
        	
        	log("========支付类预支付交易===========");
    		BigDecimal  txn_amt_num = new BigDecimal(request.getParameter("txn_amt")).multiply(BigDecimal.valueOf(100));//金额转换成分
    		String  txn_amt = String.valueOf(txn_amt_num.setScale(0));//支付金额	
            String bind_id = request.getParameter("bind_id");//获取绑定标识
            
            Map<String,String> ClientIp = new HashMap<String,String>();
            
            ClientIp.put("client_ip", "100.0.0.0");
            XMLArray.put("bind_id",bind_id);
            
            XMLArray.put("trans_id", trans_id);
            
            XMLArray.put("risk_content", ClientIp);
            
            XMLArray.put("txn_amt", txn_amt);//金额以分为单位(整型数据)并把元转换成分 

        }else if(txn_sub_type.equals("16")){
        	log("========支付类支付确认交易===========");
        	
        	String sms_code = request.getParameter("sms_code");//支付短信验证码
        	String business_no = request.getParameter("business_no");//宝付业务流水号
        	if(business_no.isEmpty()){
        		return "宝付业务流水号不能为空【business_no】";
        	}
            XMLArray.put("sms_code", sms_code);
            XMLArray.put("business_no", business_no);
        	
        }else if(txn_sub_type.equals("06")){
        	
            log("======【 交易状态查询类交易】=======");
            String orig_trans_id = request.getParameter("orig_trans_id");//订单号
            XMLArray.put("orig_trans_id", orig_trans_id);
        }else{
        	
        	return "【txn_sub_type】参数错误！";
        }
        
        
        Map<Object,Object> ArrayToObj = new HashMap<Object,Object>();
        
		 String XmlOrJson = "";		 
		 if(HeadPostParam.get("data_type").equals("xml")){
			 ArrayToObj.putAll(XMLArray);
			 XmlOrJson = MapToXMLString.converter(ArrayToObj,"data_content");
		 }else{
			 JSONObject jsonObjectFromMap = JSONObject.fromObject(XMLArray);
			 XmlOrJson = jsonObjectFromMap.toString();
		 }
        log("请求参数："+XmlOrJson);
        
        BaofooAction.getConstants().get("pfx.pwd");
		String base64str = SecurityUtil.Base64Encode(XmlOrJson);
		String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str,pfxpath,BaofooAction.getConstants().get("pfx.pwd"));	
        
		HeadPostParam.put("data_content", data_content);
        
		String PostString  = HttpUtil.RequestForm(request_url, HeadPostParam);	
		log("请求返回："+ PostString);
		
		PostString = RsaCodingUtil.decryptByPubCerFile(PostString,cerpath);
		
		if(PostString.isEmpty()){//判断解密是否正确。如果为空则宝付公钥不正确
			log("=====检查解密公钥是否正确！");
			return "检查解密公钥是否正确！";
		}
		
		PostString = SecurityUtil.Base64Decode(PostString);		 
	 	log("=====返回查询数据解密结果:"+PostString);
	
		 if(HeadPostParam.get("data_type").equals("xml")){
				PostString = JXMConvertUtil.XmlConvertJson(PostString);		    
				log("=====返回结果转JSON:"+PostString);
			}
		
		Map<String,Object> ArrayDataString = JXMConvertUtil.JsonConvertHashMap(PostString);//将JSON转化为Map对象。
		log("转换为MAP对象："+ArrayDataString);
		
		String ReturnStr = "";

		if(ArrayDataString.get("resp_code").toString().equals("0000")){
			
			ReturnStr = "返回码:"+ArrayDataString.get("resp_code")+", 返回消息:"+ArrayDataString.get("resp_msg");
			
			if(txn_sub_type.equals("11")){
				ReturnStr = PostString;
			}
			else if (txn_sub_type.equals("12")||txn_sub_type.equals("03"))
            {            	
            	ReturnStr += ", 绑定编码:"+ArrayDataString.get("bind_id");
            }
            else if(txn_sub_type.equals("15")||txn_sub_type.equals("14")){            	
            	ReturnStr = PostString;
            }
            else if (txn_sub_type.equals("16") || txn_sub_type.equals("06"))
            {
            	String MoneyStr = "(分)";
            	if(txn_sub_type.equals("06")){//查询返回单位为元
            		MoneyStr ="(元)";
            	}
            	ReturnStr +=", 成功金额：" + ArrayDataString.get("succ_amt") + MoneyStr + ", 商户订单号：" + ArrayDataString.get("trans_id");
            }
        }
		else if(txn_sub_type.equals("15")||txn_sub_type.equals("11")){
			ReturnStr = PostString;
		}
        else {
        	
        	ReturnStr = "返回码：" + ArrayDataString.get("resp_code") + ", 返回消息：" + ArrayDataString.get("resp_msg");
        }

		
		return ReturnStr;
	}
	

	private void log(String msg) {
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\t: " + msg);
	}

}
