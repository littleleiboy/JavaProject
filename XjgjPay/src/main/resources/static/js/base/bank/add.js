/**
 * 新增-银行信息字典js
 */
var vm = new Vue({
	el:'#dpLTE',
	data: {
		dicBank: {
			id: 0
		}
	},
	methods : {
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.SaveForm({
		    	url: '../../sys/bank/save?_' + $.now(),
		    	param: vm.dicBank,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})
