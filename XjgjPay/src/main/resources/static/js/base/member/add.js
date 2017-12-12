/**
 * 新增-会员信息js
 */
var vm = new Vue({
	el:'#dpLTE',
	data: {
		memberInfo: {
			id: 0
		}
	},
	methods : {
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.SaveForm({
		    	url: '../../base/member/save?_' + $.now(),
		    	param: vm.memberInfo,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})
