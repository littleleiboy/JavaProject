/**
 * 编辑-会员信息js
 */
var vm = new Vue({
	el:'#dpLTE',
	data: {
		memberInfo: {
			id: 0
		}
	},
	methods : {
		setForm: function() {
			$.SetForm({
				url: '../../base/member/info?_' + $.now(),
		    	param: vm.memberInfo.id,
		    	success: function(data) {
		    		vm.memberInfo = data;
		    	}
			});
		},
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.ConfirmForm({
		    	url: '../../base/member/update?_' + $.now(),
		    	param: vm.memberInfo,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})