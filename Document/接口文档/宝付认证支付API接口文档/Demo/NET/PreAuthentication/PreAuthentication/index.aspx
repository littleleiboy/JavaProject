<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="index.aspx.cs" Inherits="PreAuthentication.index" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>宝付认证支付API -DEMO</title>
    <style type="text/css">
<!--
.STYLE1 {
	font-size: 24px;
	font-weight: bold;
}
-->
</style>
</head>
<body style="margin:0 auto;">
<div style="margin:0 auto; width:500px;">
	<p class="STYLE1">&nbsp;</p>
	<p class="STYLE1">宝付认证支付API -DEMO</p>
	<p>&nbsp;</p>
	<p>01:<a href="bind.aspx">实名建立绑定关系类交易</a>,</p>
	<p>02:<a href="removebind.aspx">解除绑定关系类交易</a>,  </p>
	<p>03:<a href="querybind.aspx">查询绑定关系类交易</a>,  </p>
	<p>04:<a href="pay.aspx">支付类交易</a>,  </p>
	<p>06:<a href="querytransid.aspx">交易状态查询类交易</a> </p>
</div>
</body>
</html>
