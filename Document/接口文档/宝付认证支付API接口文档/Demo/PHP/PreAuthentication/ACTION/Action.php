<?php
/**
 * 本实例公供学习对接《宝付认证API4.0.0》接口使用
 * 加密方法为RSA，数据类型JSON/XML
 * 实例仅供参考，实例只展示（宝付认证API4.0.0接口的请求数据格式、加密方式、请求方式），商户可根据以下代码编码自已的原程序，
 * 
 * 宝付测试专用账号  
 *	银行卡号 		发卡行名称	姓名	身份证号			手机号
 *       6222020111122220000	工商银行	张宝	320301198502169142	对接人员手机号	
 * 
 * 注意：测试环境返回的“bind_id”为固定值，短信不真实发送可随意填写
 * 
 * @作者：宝付技术（大圣）
 */
require_once '../Config/init.php';

//==================接收用户数据==========================
//ob_start ();
$pay_code = isset($_POST["pay_code"])? $_POST["pay_code"] :"";//银行卡编码
$acc_no = isset($_POST["acc_no"])? trim($_POST["acc_no"]):"";//银行卡卡号
$id_card = isset($_POST["id_card"])? trim($_POST["id_card"]):"";//身份证号码
$id_holder = isset($_POST["id_holder"])? trim($_POST["id_holder"]):"";//姓名
$mobile = isset($_POST["mobile"])? trim($_POST["mobile"]):"";//银行预留手机号
$next_txn_sub_type = isset($_POST["next_txn_sub_type"])? trim($_POST["next_txn_sub_type"]):"";//下一下进行的交易子类
$trans_id = isset($_POST["trans_id"])? trim($_POST["trans_id"]):"";	//商户订单号
$txn_sub_type = isset($_POST["txn_sub_type"])? trim($_POST["txn_sub_type"]):"";
/*
 *txn_sub_type 交易子类
 *

	11:预绑卡类交易,
	12:确认绑卡类交易,
	02:解除绑定关系类交易,
	03:查询绑定关系类交易,
	14、15:认证支付类预支付交易,（14不发短信，15发送短信）需要申请开通默认为15。
	16:认证支付类支付确认交易，
 *      06：交易状态查询类交易。

*/

//==============固定参数================================

$biz_type = "0000";//接入类型
$id_card_type="01";//证件类型固定01（身份证） 
$acc_pwd="";//银行卡密码（传空）
$valid_date = "";//卡有效期 （传空）
$valid_no ="";//卡安全码（传空）
$additional_info="附加字段";//附加字段
$req_reserved="保留";//保留
//$rquest_url = "https://tgw.baofoo.com/cutpayment/api/backTransRequest"; //测试环境请求地址
//$rquest_url = "https://public.baofoo.com/cutpayment/api/backTransRequest" //正式环境请求地址

//====================系统动态生成值=======================================
$trans_serial_no = "TSN".get_transid().rand4();	//商户流水号

$trade_date = return_time();	//订单日期

//================报文组装=================================


$data_content_parms = array('txn_sub_type' =>$txn_sub_type,
                            'biz_type' =>$biz_type,
                            'terminal_id' =>$terminal_id,
                            'member_id' =>$member_id,
                            'trans_serial_no' =>$trans_serial_no,
                            'trade_date' =>$trade_date,
                            'additional_info' =>$additional_info,
                            'req_reserved' =>$req_reserved);


if($txn_sub_type == "11"){ //01:预绑卡类交易,
    Log::LogWirte("==========预绑卡类交易============");
	
        if(!isset($_POST["trans_id"])){
            echo "参数错误【trans_id】不能为空！";
            Log::LogWirte("参数错误【trans_id】不能为空");
            die();
        }
	$data_content_parms["acc_no"] = $acc_no;
	$data_content_parms["trans_id"] =$trans_id ;
	$data_content_parms["id_card_type"] =$id_card_type ;
	$data_content_parms["id_card"] =$id_card ;
	$data_content_parms["id_holder"] =$id_holder ;
	$data_content_parms["mobile"] =$mobile ;
	$data_content_parms["acc_pwd"] =$acc_pwd ;
	$data_content_parms["valid_date"] =$valid_date ;
	$data_content_parms["valid_no"] =$valid_no ;
	$data_content_parms["pay_code"] =$pay_code ;

}elseif($txn_sub_type == "12"){ //12:确认绑卡类交易,
    Log::LogWirte("==========【确认绑卡类交易】============");
        if(!isset($_POST["trans_id"])){
            echo "参数错误【trans_id】不能为空！,注：需和预绑卡交易订单号一致";
            Log::LogWirte("参数错误【trans_id】不能为空！,注：需和预绑卡交易订单号一致");
            die();
        }
        if(!isset($_POST["sms_code"])){
            echo "参数错误【sms_code】不能为空！";
            Log::LogWirte("参数错误【sms_code】不能为空！");
            die();
        }
        
        $sms_code = isset($_POST["sms_code"])? trim($_POST["sms_code"]):"";//短信验证码
	$data_content_parms["sms_code"] =$sms_code ;
	$data_content_parms["trans_id"] =$trans_id ;

}elseif ($txn_sub_type =="02") {
        Log::LogWirte("==========【解除绑定关系类交易】============");
        if(!isset($_POST["bind_id"])){
            echo "参数错误【bind_id】不能为空！";
            Log::LogWirte("参数错误【bind_id】不能为空！");
            die();
        }
        $bind_id = isset($_POST["bind_id"])? trim($_POST["bind_id"]):"";//短信验证码
	$data_content_parms["bind_id"] =$bind_id ;
        
}elseif($txn_sub_type == "03"){ //03:查询绑定关系类交易
        Log::LogWirte("==========【查询绑定关系类交易】============");
        if(!isset($_POST["acc_no"])){
            echo "参数错误【acc_no】不能为空！";
            Log::LogWirte("参数错误【acc_no】不能为空！");
            die();
        }
	$data_content_parms["acc_no"] =$acc_no ;
	
}elseif($txn_sub_type == "15"){ //15:认证支付类预支付交易
        Log::LogWirte("==========【认证支付类预支付交易】============");
        if(!isset($_POST["bind_id"])){
            echo "参数错误【bind_id】不能为空！";
            Log::LogWirte("参数错误【bind_id】不能为空！");
            die();
        }
        $ClientIp["client_ip"] ="100.0.0.0";//传用户的IP地址
 	$txn_amt = isset($_POST["txn_amt"])? trim($_POST["txn_amt"]):"";//交易金额额
	$txn_amt *=100;//金额以分为单位（把元转换成分）        
	$data_content_parms["bind_id"] =trim($_POST["bind_id"]);//获取绑定标识	
	$data_content_parms["trans_id"] ="TI".get_transid().rand4() ;//商户订单号
        $data_content_parms["risk_content"] =$ClientIp ;
	$data_content_parms["txn_amt"] =$txn_amt ;
	
}elseif($txn_sub_type == "16"){ //16:认证支付类支付确认交易,
        Log::LogWirte("==========【认证支付类支付确认交易】============");
        
        if(!isset($_POST["sms_code"])){
            echo "参数错误【sms_code】不能为空！";
            Log::LogWirte("参数错误【sms_code】不能为空！");
            die();
        }
        if(!isset($_POST["business_no"])){
            echo "参数错误【business_no】不能为空！";
            Log::LogWirte("参数错误【business_no】不能为空！");
            die();
        }
        $data_content_parms["business_no"] =$_POST["business_no"] ;
        $data_content_parms["sms_code"]=$_POST["sms_code"];
		
}elseif($txn_sub_type == "06"){ //06:交易状态查询类交易
        Log::LogWirte("==========【交易状态查询类交易】============");
	$orig_trans_id = isset($_POST["orig_trans_id"])? trim($_POST["orig_trans_id"]):"";//订单号	 
	$data_content_parms["orig_trans_id"]=$orig_trans_id;
}else{
	echo 'txn_sub_type参数缺失';
}
//==================转换数据类型=============================================
if($data_type == "json"){
	$Encrypted_string = str_replace("\\/", "/",json_encode($data_content_parms));//转JSON
}else{
	$toxml = new SdkXML();	//实例化XML转换类
	$Encrypted_string = $toxml->toXml($data_content_parms);//转XML
}

Log::LogWirte("序列化结果：".$Encrypted_string);
$BFRsa = new BFRSA($GLOBALS["pfxfilename"], $GLOBALS["cerfilename"], $GLOBALS["private_key_password"],TRUE); //实例化加密类。
$Encrypted = $BFRsa->encryptedByPrivateKey($Encrypted_string);	//先BASE64进行编码再RSA加密
$PostArry = array("version" => $version,
    "terminal_id" =>$GLOBALS["terminal_id"],
    "txn_type" => $GLOBALS["txn_type"],
    "txn_sub_type" => $txn_sub_type,
    "member_id" => $GLOBALS["member_id"],
    "data_type" => $GLOBALS["data_type"],
    "data_content" => $Encrypted);
$return = HttpClient::Post($PostArry, $request_url);  //发送请求到宝付服务器，并输出返回结果。
Log::LogWirte("请求返回参数：".$return);

if(empty($return)){
    throw new Exception("返回为空，确认是否网络原因！");
}

$return_decode = $BFRsa->decryptByPublicKey($return);//解密返回的报文
Log::LogWirte("解密结果：".$return_decode);
$endata_content = array();
if(!empty($return_decode)){//解析XML、JSON
    if($data_type =="xml"){
        $endata_content = SdkXML::XTA($return_decode);
    }else{
        $endata_content = json_decode($return_decode,TRUE);
    }
    $Mstr=$endata_content;
    if(is_array($endata_content) && (count($endata_content)>0)){
        if(array_key_exists("resp_code", $endata_content)){
            if($endata_content["resp_code"] == "0000"){
                $return_decode = "订单状态码：".$endata_content["resp_code"].",返回消息：".$endata_content["resp_msg"];
                if($txn_sub_type =="11"){
                    $return_decode = str_replace("\\/", "/",json_encode($Mstr)) ;
                }elseif ($txn_sub_type =="12" || $txn_sub_type=="03") {
                    $return_decode .= ", 绑定编码:".$endata_content["bind_id"];
                }elseif ($txn_sub_type =="02") {
                    
                }elseif ($txn_sub_type =="15" || $txn_sub_type=="14") {
                    $return_decode = str_replace("\\/", "/",json_encode($Mstr)) ;
                }elseif($txn_sub_type =="16" || $txn_sub_type=="06"){
                    $MoneyStr = "(分)";
                    if($txn_sub_type=="06"){//查询返回单位为元
            		$MoneyStr ="(元)";
                    }
                    $return_decode .=", 成功金额：".$endata_content["succ_amt"].$MoneyStr.", 商户订单号：".$endata_content["trans_id"];
                }else{
                    throw new Exception("解析异常");
                }
            }elseif ($txn_sub_type =="15" || $txn_sub_type =="11") {
                $return_decode = str_replace("\\/", "/",json_encode($Mstr));
            }else{
                //错误或失败其他状态
                $return_decode = "订单状态码：".$endata_content["resp_code"].", 返回消息：".$endata_content["resp_msg"];
            }
            echo $return_decode;//输出
            die();
        }else{
            throw new Exception("[resp_code]返回码不存在!");
        }
    }
}  else {
    echo "请求出错，请检查网络";
}
die();



function get_transid(){//生成时间戳

	return strtotime(date('Y-m-d H:i:s',time()));
	
}
function rand4(){//生成四位随机数

	return rand(1000,9999);
	
}
function return_time(){//生成时间

	return date('YmdHis',time());
	
}



?>