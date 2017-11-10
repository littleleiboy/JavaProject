<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查询绑定关系</title>
    <script src="Scripts/jquery-1.4.1.min.js" type="text/javascript"></script>
    <script>
        $(document).ready(function () {
            $("#R03").submit(function (e) {
                if ($.trim($("#acc_no").val()) == "") {
                    alert("输入银行卡号！");
                    return false;
                }
            });

        });
</script>
</head>
<body>
 <form name="R03" id="R03" method="post" action="Api.action"> 
<div style="margin:0 auto; width:500px;">
<table width="500" height="152" border="0" cellpadding="1" cellspacing="1" bgcolor="#33CCFF">
  <tr>
    <td height="84" colspan="2" align="center" bgcolor="#FFFFFF"><span class="STYLE1">认证支付API查询绑定关系-DEMO</span></td>
  </tr>

  
  <tr>
    <td width="108" align="right" bgcolor="#FFFFFF">银行卡号：</td>
    <td width="392" bgcolor="#FFFFFF"><input name="acc_no" type="text" id="acc_no" value="" size="20" maxlength="20" /></td>
  </tr>
  
  <tr>
    <td colspan="2" align="center" bgcolor="#FFFFFF"> <input name="trans_id" type="hidden" id="trans_id" value="" />
	<input name="txn_sub_type" type="hidden" id="txn_sub_type" value="03"/>
    <input type="submit" name="Submit" value="查 询" /></td>
    </tr>
	
</table>

</div>
    </form>
</body>
</html>