<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<div style="margin:0 auto; width:600px;">
 <form name="R06" id="R06" method="post" action="Api.action"> 
<table width="100%" height="152" border="0" cellpadding="1" cellspacing="1" bgcolor="#33CCFF">
  <tr>
    <td height="84" colspan="2" align="center" bgcolor="#FFFFFF"><span class="STYLE1">认证支付API交易状态查询-DEMO</span></td>
  </tr>

  
  <tr>
    <td width="88" align="right" bgcolor="#FFFFFF">订单号：</td>
    <td width="505" bgcolor="#FFFFFF"><input name="orig_trans_id" type="text" id="orig_trans_id" value="" size="30" maxlength="30" />
      <span class="STYLE2">（注意：此订单号为商户订单号 ：trans_id）</span></td>
  </tr>
  
  <tr>
    <td colspan="2" align="center" bgcolor="#FFFFFF"> <input name="trans_id" type="hidden" id="trans_id" value="" />
	<input name="txn_sub_type" type="hidden" id="txn_sub_type" value="06"/>
    <input type="submit" name="Submit" value="查 询 订 单" /></td>
    </tr>

</table>
	</form>
</div>
</body>
</html>