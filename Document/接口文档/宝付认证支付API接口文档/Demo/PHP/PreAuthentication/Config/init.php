<?php
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

\header("Content-type: text/html; charset=utf-8"); 
//====================配置商户的宝付接口授权参数============================
$path = $_SERVER['DOCUMENT_ROOT'];
$pathcer = $path."/CER/";	//证书路径

require_once($path."/Function/BFRSA.php");
require_once($path."/Function/SdkXML.php");
require_once($path."/Function/Log.php");
require_once($path."/Function/HttpClient.php");

Log::LogWirte("=================认证API=====================");
//====================配置商户的宝付接口授权参数==============

$version = "4.0.0.0";//版本号
$member_id = "100000276";	//商户号
$terminal_id = "100000990";	//终端号
$data_type="xml";//加密报文的数据类型（xml/json）
$txn_type = "0431";//交易类型

$private_key_password = "123456";	//商户私钥证书密码
$pfxfilename = $pathcer."bfkey_100000276@@100000990.pfx";  //注意证书路径是否存在
$cerfilename = $pathcer."bfkey_100000276@@100000990.cer";//注意证书路径是否存在

$request_url = "http://vgw.baofoo.com/cutpayment/api/backTransRequest";  //测试环境请求地址
//$request_url = "https://public.baofoo.com/livesplatform/api/backTransRequest";  //正试环境请求地址


if(!file_exists($pfxfilename))
{
    die("私钥证书不存在！<br>");
}
if(!file_exists($cerfilename))
{
    die("公钥证书不存在！<br>");
}

