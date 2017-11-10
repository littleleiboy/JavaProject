using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Configuration;
using RSACode;
using PreAuthentication.Function;

/**
 * 
 * 本实例公供学习对接《宝付认证API4.0.0》接口使用
 * 加密方法为RSA，数据类型JSON/XML
 * 实例仅供参考，实例只展示（宝付认证API4.0.0接口的请求数据格式、加密方式、请求方式），商户可根据以下代码编码自已的原程序，
 * 引用:Newtonsoft.Json.dll,开源BouncyCastle
 *
 * 宝付测试专用账号  
 *	银行卡号 		发卡行名称	姓名	身份证号			手机号
 *  6222020111122220000	工商银行	张宝	320301198502169142	对接人员手机号	
 * 注意：测试环境返回的“bind_id”为固定值，短信不真实发送可随意填写
 * 
 * @作者：宝付技术（大圣）
 */

namespace PreAuthentication.ACTION
{
    public partial class Actions : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            Log.LogWrite("");
            String txn_sub_type = Request.Params["txn_sub_type"];

            if (String.IsNullOrEmpty(txn_sub_type))
            {
                Response.Write("参数【txn_sub_type】不能为空");
                Response.End();
            }


            String pay_code = Request.Params["pay_code"];//银行卡编码
            String acc_no = Request.Params["acc_no"];//银行卡卡号
            String id_card = Request.Params["id_card"];//身份证号码
            String id_holder = Request.Params["id_holder"];//姓名
            String mobile = Request.Params["mobile"];//银行预留手机号
            String next_txn_sub_type = Request.Params["next_txn_sub_type"];//下一下进行的交易子类
            String trans_id = Request.Params["trans_id"];	//商户订单号

            Dictionary<String, String> HeadPostParam = new Dictionary<String, String>();

            HeadPostParam.Add("version", "4.0.0.0");
            HeadPostParam.Add("member_id", ConfigurationManager.AppSettings["MemberId"]);
            HeadPostParam.Add("terminal_id", ConfigurationManager.AppSettings["TerminalId"]);
            HeadPostParam.Add("txn_type", "0431");
            HeadPostParam.Add("txn_sub_type", txn_sub_type);
            HeadPostParam.Add("data_type", ConfigurationManager.AppSettings["DataType"]);

            /**
             * 报文体
             * =============================================
             * 
             */
            Dictionary<String, Object> XMLArray = new Dictionary<String, Object>();

            /**
             * 
             * 公共参数
             * 
             */
            XMLArray.Add("txn_sub_type", HeadPostParam["txn_sub_type"]);
            XMLArray.Add("biz_type", "0000");
            XMLArray.Add("terminal_id", HeadPostParam["terminal_id"]);
            XMLArray.Add("member_id", HeadPostParam["member_id"]);
            XMLArray.Add("trans_serial_no", GetTSNID());
            XMLArray.Add("trade_date", DateTime.Now.ToString("yyyyMMddHHmmss"));
            XMLArray.Add("additional_info", "附加信息");
            XMLArray.Add("req_reserved", "保留");


            if (txn_sub_type.Equals("11")) {
                Log.LogWrite("=============================【 (" + txn_sub_type + ")预绑卡类交易】=======================================");
                if (String.IsNullOrEmpty(trans_id))
                {
                    Response.Write("商户订单号不能为空【trans_id】");
                    Log.LogWrite("商户订单号不能为空【trans_id】");
                    return;
                }

                XMLArray.Add("acc_no", acc_no);//商户订单号
                XMLArray.Add("trans_id", trans_id);//商户订单号
                XMLArray.Add("id_card_type", "01");//证件类型固定01（身份证）
                XMLArray.Add("id_card", id_card);
                XMLArray.Add("id_holder", id_holder);
                XMLArray.Add("mobile", mobile);
                XMLArray.Add("acc_pwd", "");
                XMLArray.Add("valid_date", "");
                XMLArray.Add("valid_no", "");
                XMLArray.Add("pay_code", pay_code);

            }
            else if (txn_sub_type.Equals("12"))
            {
                Log.LogWrite("=============================【 (" + txn_sub_type + ")确认绑卡交易】=======================================");
                String sms_code = Request.Params["sms_code"];//短信验证码
                if (String.IsNullOrEmpty(trans_id))
                {
                    Response.Write("商户订单号不能为空【trans_id】,注：需和预绑卡交易订单号一致");
                    Log.LogWrite("商户订单号不能为空【trans_id】,注：需和预绑卡交易订单号一致");
                    return;
                }
                if (String.IsNullOrEmpty(sms_code))
                {
                    Response.Write("短信验证码不能为空【sms_code】");
                    Log.LogWrite("短信验证码不能为空【sms_code】");
                    return;
                }
                XMLArray.Add("trans_id", trans_id);
                XMLArray.Add("sms_code", sms_code);
            
            }
            else if (txn_sub_type.Equals("02"))
            {
                Log.LogWrite("=============================【 (" + txn_sub_type + ")解除绑卡交易】=======================================");
                String bind_id = Request.Params["bind_id"];//获取绑定标识	 
	            XMLArray.Add("bind_id",bind_id);
            }
            else if (txn_sub_type.Equals("03"))
            {
                Log.LogWrite("=============================【 (" + txn_sub_type + ")查询绑定关系类交易】=======================================");
                XMLArray.Add("acc_no",acc_no) ;//卡号
            }
            else if (txn_sub_type.Equals("15"))
            {
                Log.LogWrite("=============================【 (" + txn_sub_type + ")支付类预支付交易】=======================================");
                Dictionary<String, Object> ClientIp = new Dictionary<String, Object>();
                ClientIp.Add("client_ip", "100.0.0.0");
                String TxnAmt = Request.Params["txn_amt"];//交易金额额
                String bind_id = Request.Params["bind_id"];//获取绑定标识

	            XMLArray.Add("bind_id",bind_id);
                XMLArray.Add("trans_id", GetTransId());
                XMLArray.Add("txn_amt", decimal.Round((decimal.Parse(TxnAmt) * 100), 0).ToString());//金额以分为单位(整型数据)并把元转换成分
                XMLArray.Add("risk_content", ClientIp);
            }
            else if (txn_sub_type.Equals("16"))
            {
                Log.LogWrite("=============================【 (" + txn_sub_type + ")支付类支付确认交易】=======================================");
                String sms_code = Request.Params["sms_code"];//交易金额额
                String business_no = Request.Params["business_no"];//宝付业务流水号
                if (String.IsNullOrEmpty(business_no))
                {
                    Response.Write("宝付业务流水号不能为空【business_no】");
                    Log.LogWrite("宝付业务流水号不能为空【business_no】");
                    return;
                }
                XMLArray.Add("sms_code", sms_code);
                XMLArray.Add("business_no", business_no);

            }
            else if (txn_sub_type.Equals("06"))
            {
                Log.LogWrite("=============================【 (" + txn_sub_type + ")订单查询接口】=======================================");
                String orig_trans_id = Request.Params["orig_trans_id"];//订单号
                XMLArray.Add("orig_trans_id", orig_trans_id);
            }
            else {

                Response.Write("[next_txn_sub_type]参数错误！");
                Response.End();
            
            }
            String TempRSAStr = ToXMLJSON.ObjectToXmlJson(XMLArray, HeadPostParam["data_type"]);//JSON或XML由date_type确定
            Log.LogWrite("【" + HeadPostParam["txn_sub_type"] + "序例化（" + HeadPostParam["data_type"] + "）：】" + TempRSAStr);

            TempRSAStr = RSAUtil.EncryptRSAByPfx(TempRSAStr, HttpContext.Current.Server.MapPath(ConfigurationManager.AppSettings["PfxPath"]), ConfigurationManager.AppSettings["PfxPwd"]);
            Log.LogWrite("【" + HeadPostParam["txn_sub_type"] + "报文加密：】" + TempRSAStr);//记录日志

            HeadPostParam.Add("data_content", TempRSAStr);

            String PostParam = HttpsRequest.GetParam(HeadPostParam);
            Log.LogWrite("【" + HeadPostParam["txn_sub_type"] + "请求参数拼接：】" + PostParam);

            String HTTPReturn = HttpsRequest.OpenReadWithHttps(ConfigurationManager.AppSettings["RUrl"], PostParam);

            Log.LogWrite("【" + HeadPostParam["txn_sub_type"] + "返回报文：】" + HTTPReturn);

            TempRSAStr = RSAUtil.DecryptRSAByCer(HTTPReturn, Server.MapPath(ConfigurationManager.AppSettings["CerPath"]));


            if (String.IsNullOrEmpty(TempRSAStr))
            {
                Log.LogWrite("【解密结果】：解密失败");
                Response.Write("解密失败！{" + TempRSAStr + "}<br>");
                Response.End();
            }

            string JSONstring = "";
            if (HeadPostParam["data_type"].Equals("xml"))
            {
                JSONstring = JXDConvert.XmlToJson(TempRSAStr);
            }
            else
            {
                JSONstring = TempRSAStr;
            }

            Log.LogWrite("【返回明文解密：】"+JSONstring);
            Dictionary<String, Object> DJSON = JXDConvert.JsonToDictionary(JSONstring);
            Dictionary<String, Object> DJSONS = DJSON;
            if(HeadPostParam["data_type"].Equals("xml")){

                DJSON = (Dictionary<String, Object>)DJSON["result"];
            
            }

                
            
            Log.LogWrite("【对象转换】：" + DJSON["resp_code"]);
            String ReturnStr = "";
            if (DJSON["resp_code"].Equals("0000"))
            {

                //此处商户可写自已的处理程序

                ReturnStr = "返回码：" + DJSON["resp_code"] + " 返回消息：" + DJSON["resp_msg"];

                if (txn_sub_type.Equals("11")) {
                    ReturnStr = ToXMLJSON.ObjectToXmlJson(DJSONS["result"], "JSON");
                }
                else if (txn_sub_type.Equals("12") || txn_sub_type.Equals("03"))
                {
                    ReturnStr += ", 绑定编码:" + DJSON["bind_id"];
                }
                else if (txn_sub_type.Equals("15") || txn_sub_type.Equals("14"))
                {
                    ReturnStr = ToXMLJSON.ObjectToXmlJson(DJSONS["result"], "JSON");
                }
                else if (txn_sub_type.Equals("16") || txn_sub_type.Equals("06"))
                {
                    String MoneyStr = "(分)";
                    if (txn_sub_type.Equals("06"))
                    {//查询返回单位为元
                        MoneyStr = "(元)";
                    }
                    ReturnStr += ", 成功金额：" + DJSON["succ_amt"] + MoneyStr + ", 商户订单号：" + DJSON["trans_id"];

                }
            }
            else if (txn_sub_type.Equals("15") || txn_sub_type.Equals("11"))
            {
                ReturnStr = ToXMLJSON.ObjectToXmlJson(DJSONS["result"], "JSON");
            }
            else 
            {
                ReturnStr = "返回码：" + DJSON["resp_code"] + ", 返回消息：" + DJSON["resp_msg"];
            }
            Response.Write(ReturnStr);
            Log.LogWrite("=============================【 (" + txn_sub_type + ")结束】=======================================");
        }

        private string GetTSNID()
        {
            return "TSNNET" + GetTimeStamp() + RandomString();
        }


        private string GetTransId()
        {
            return "TIDNET" + GetTimeStamp() + RandomString();
        }

        private string GetTimeStamp()   //获取时间戳
        {
            TimeSpan ts = DateTime.UtcNow - new DateTime(1970, 1, 1, 0, 0, 0, 0);
            return Convert.ToInt64(ts.TotalMilliseconds).ToString();
        }

        private string RandomString() //生成四位随机数
        {
            Random rad = new Random();
            int value = rad.Next(1000, 10000);
            return value.ToString();
        }
    }
}