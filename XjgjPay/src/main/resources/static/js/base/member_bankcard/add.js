/**
 * 新增-会员绑定银行卡信息js
 */
var vm = new Vue({
	el:'#dpLTE',
	data: {
		memberBankcard: {
			id: 0
		}
	},
	methods : {
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.SaveForm({
		    	url: '../../base/member_bankcard/save?_' + $.now(),
		    	param: vm.memberBankcard,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})
