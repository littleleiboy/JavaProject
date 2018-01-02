/**
 * 圈提
 * @author Tian
 * @date 2017-12-18
 */
var F

(function($,doc){
	$.plusReady(function(){
		var clickButton = document.getElementById('btnReg');
		clickButton.addEventListener('tap',function(event){
			var url = '';
			var memberName = document.getElementById('memberName').value.trim();
			var memberNo = document.getElementById('memberNo').value.trim();
			var passWord = document.getElementById('passWord').value.trim();
			var withDrawQty = document.getElementById('withDrawQty').value.trim();
			if(memberName == null ||memberName == ''){
				mui.confirm("会员姓名不能为空")
			}
			else if(memberNo == null || memberNo ==''){
				mui.confirm("会员账号不能为空")
			}
			else if(passWord == null || passWord==''){
				mui.confirm("主卡密码不能为空")
			}
			else if(withDrawQty <= 0){
				mui.confirm("圈提金额不能为0")
			}
			mui.ajax(url,{
				data:{
					"memberName":memberName,
					"memberNo":memberNo,
					"passWord":passWord,
					"withDrawQty":withDrawQty,
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
