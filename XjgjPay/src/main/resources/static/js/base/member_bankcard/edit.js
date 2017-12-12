/**
 * 编辑-会员绑定银行卡信息js
 */
var vm = new Vue({
	el:'#dpLTE',
	data: {
		memberBankcard: {
			id: 0
		}
	},
	methods : {
		setForm: function() {
			$.SetForm({
				url: '../../base/member_bankcard/info?_' + $.now(),
		    	param: vm.memberBankcard.id,
		    	success: function(data) {
		    		vm.memberBankcard = data;
		    	}
			});
		},
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.ConfirmForm({
		    	url: '../../base/member_bankcard/update?_' + $.now(),
		    	param: vm.memberBankcard,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})