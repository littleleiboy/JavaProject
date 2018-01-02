/**
 * 圈提
 * @author Tian
 * @date 2018-01-02
 */
var F

(function($,doc){
	$.plusReady(function(){
		var clickButton = document.getElementById('btnBind');
		clickButton.addEventListener('tap',function(event){
			var url = '';
			var memberName = document.getElementById('memberName').value.trim();
			var memberNo = document.getElementById('memberNo').value.trim();
			var bankName = document.getElementById('bankName').value.trim();
			var IDCardNo = document.getElementById('IDCardNo').value.trim();
			var bankAccountNo = document.getElementById('bankAccoutnNo').value.trim();
			var passWord = document.getElementById('passWord').value.trim();
			
			if(memberName == null ||memberName == ''){
				mui.confirm("会员姓名不能为空")
			}
			else if(memberNo == null || memberNo ==''){
				mui.confirm("会员账号不能为空")
			}
			else if(bankName == null || bankName ==''){
				mui.confirm("开户姓名不能为空")
			}
			else if(IDCardNo == null || IDCardNo ==''){
				mui.confirm("开户身份证号不能为空")
			}
			else if(bankAccountNo == null || bankAccountNo ==''){
				mui.confirm("中国银行卡号不能为空")
			}
			else if(passWord == null || passWord==''){
				mui.confirm("主卡密码不能为空")
			}
			
			mui.ajax(url,{
				data:{
					"memberName":memberName,
					"memberNo":memberNo,
					"bankName":bankName,
					"IDCardNo":IDCardNo,
					"bankAccountNo":bankAccountNo,
					"passWord":passWord
				},
				dataType:"json",
				type:"post",
				timeout:10000,
				success:function(data){
					
				},
				error: function(xhr,type,errorThrown){
					
				}
			});
		})
	})
}(mui,document));
