/**
 * 编辑-银行信息字典js
 */
var vm = new Vue({
	el:'#dpLTE',
	data: {
		dicBank: {
			id: 0
		}
	},
	methods : {
		setForm: function() {
			$.SetForm({
				url: '../../sys/bank/info?_' + $.now(),
		    	param: vm.dicBank.id,
		    	success: function(data) {
		    		vm.dicBank = data;
		    	}
			});
		},
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.ConfirmForm({
		    	url: '../../sys/bank/update?_' + $.now(),
		    	param: vm.dicBank,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})