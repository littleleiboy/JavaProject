<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>实名绑定类交易</title>
<script src="Scripts/jquery-1.4.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
    $(document).ready(function () {

        var wait = 60;
        var Timestamp;
        function time(o) {
            if ($.trim($("#acc_no").val()) == "") {
                alert("请输入银行卡号！");
                return false;
            }
            if ($.trim($("#id_card").val()) == "") {
                alert("请输入身份证号！");
                return false;
            }
            if ($.trim($("#mobile").val()) == "") {
                alert("请输入手机号！");
                return;
            }

            if ($.trim($("#id_holder").val()) == "") {
                alert("请输入姓名！");
                return;
            }

            if (wait == 0) {
                o.value = "获取验证码";
                o.removeAttribute("disabled");
                wait = 60;
                return;
            }
            if (wait == 60) {

                Timestamp = "TIDNET" + Date.parse(new Date());
                $("#trans_id").val(Timestamp);
                o.setAttribute("disabled", true);
                o.value = "获取验证码";
                htmlobj = $.ajax({ type: "POST",
                    url: "Api.action",
                    datatype: "xml",
                    data: { mobile: $.trim($("#mobile").val()),
                        acc_no: $.trim($("#acc_no").val()),
                        id_card: $.trim($("#id_card").val()),
                        id_holder: $.trim($("#id_holder").val()),
                        pay_code: $.trim($("#pay_code").val()),
                        txn_sub_type: "11",
                        trans_id: $.trim($("#trans_id").val())
                    },
                    success: function (data) {
                    	var parsedJson = jQuery.parseJSON(data);
                        if (parsedJson.resp_code == "0000") {
                            alert("短信发送成功！");
                        } else {
                            alert("短信发送失败【 "+parsedJson.resp_msg+" 】");
                        }
                    },
                    complete: function (XMLHttpRequest, textStatus) {
                        alert("请求短信成功！【" + textStatus + "】");
                    },
                    error: function () {
                        alert("本地Actions页面异常！");
                    }
                });
                wait = 59;
                setTimeout(function () { time(o); }, 1000);
            } else {
                o.value = "重新发送(" + wait + ")";
                wait--;
                setTimeout(function () { time(o); }, 1000);
            }
        }
        $("#btn").click(function () { time(this); });
        $("#R01").submit(function (e) {
            if ($.trim($("#sms_code").val()) == "") {
                alert("请输入验证码！");
                return false;
            }
        });

    });

</script>

</head>
<body style="margin:0">
<div style="margin:0 auto; width:500px;">
 <form name="R01" id="R01" method="post" action="Api.action"> 
<table width="500" height="325" border="0" cellpadding="1" cellspacing="1" bgcolor="#33CCFF">
  <tr>
    <td height="84" colspan="2" align="center" bgcolor="#FFFFFF"><span class="STYLE1">认证支付API实名绑定类交易-DEMO</span></td>
  </tr>
  <tr>
    <td width="108" align="right" bgcolor="#FFFFFF">银行名称：</td>
    <td width="392" bgcolor="#FFFFFF"><select name="pay_code" id="pay_code">
	<option value="ICBC" selected="selected">中国工商银行</option>
<option value="ABC">中国农业银行</option>
<option value="CCB">中国建设银行</option>
<option value="BOC">中国银行</option>
<option value="BCOM">中国交通银行</option>
<option value="CIB">兴业银行</option>
<option value="CITIC">中信银行</option>
<option value="CEB">中国光大银行</option>
<option value="PAB">平安银行</option>
<option value="PSBC">中国邮政储蓄银行</option>
<option value="SHB">上海银行</option>
<option value="SPDB">浦东发展银行</option>
    </select>    </td>
  </tr>
  <tr>
    <td align="right" bgcolor="#FFFFFF">银行卡号：</td>
    <td bgcolor="#FFFFFF"><input name="acc_no" type="text" id="acc_no" size="20" maxlength="20" /></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#FFFFFF">身份证号：</td>
    <td bgcolor="#FFFFFF"><input name="id_card" type="text" id="id_card" size="18" maxlength="18" /></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#FFFFFF">手机号：</td>
    <td bgcolor="#FFFFFF"><input name="mobile" type="text" id="mobile" size="11" maxlength="11" /></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#FFFFFF">姓名：</td>
    <td bgcolor="#FFFFFF"><input name="id_holder" type="text" id="id_holder" size="10" maxlength="10" /></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#FFFFFF">短信验证码：</td>
    <td bgcolor="#FFFFFF"><input name="sms_code" type="text" id="sms_code" size="6" maxlength="6" />
    <input id="btn" name="btn" type="button" value="获取验证码" /></td>
  </tr>  
  <tr>
    <td colspan="2" align="center" bgcolor="#FFFFFF">
	<input name="trans_id" type="hidden" id="trans_id" value="" />
	<input name="txn_sub_type" type="hidden" id="txn_sub_type" value="12" size="5" maxlength="5" />
    <input type="submit" name="Submit" value="申 请 绑 定" /></td>
    </tr>
	
</table>
</form>
</div>
</body>
</html>