<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>解除绑定关系</title>
<script src="Scripts/jquery-1.4.1.min.js" type="text/javascript"></script>
<!--
.STYLE1 {
	font-size: 24px;
	font-weight: bold;
}
-->
<script>
    $(document).ready(function () {
        $("#R03").submit(function (e) {
            if ($.trim($("#bind_id").val()) == "") {
                alert("输入绑定标识号！");
                return false;
            }
        });

    });
</script>
</style>
</head>
<body>
<div style="margin:0 auto; width:500px;">
 <form name="R03" id="R03" method="post" action="Api.action"> 
<table width="500" height="152" border="0" cellpadding="1" cellspacing="1" bgcolor="#33CCFF">
  <tr>
    <td height="84" colspan="2" align="center" bgcolor="#FFFFFF"><span class="STYLE1">认证支付API解除绑定关系-DEMO</span></td>
  </tr>
  <tr>
    <td width="108" align="right" bgcolor="#FFFFFF">绑定标识号：</td>
    <td width="392" bgcolor="#FFFFFF"><input name="bind_id" type="text" id="bind_id" size="30" maxlength="30" /></td>
  </tr>
  
  <tr>
    <td colspan="2" align="center" bgcolor="#FFFFFF"><input name="txn_sub_type" type="hidden" id="txn_sub_type" value="02"/>
    <input type="submit" name="Submit" value="解 除 绑 定" /></td>
    </tr>

</table>

</div>
	</form>
</body>
</html>