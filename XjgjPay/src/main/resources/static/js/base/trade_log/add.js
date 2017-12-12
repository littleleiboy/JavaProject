/**
 * 新增-交易记录js
 */
var vm = new Vue({
	el:'#dpLTE',
	data: {
		tradeLog: {
			id: 0
		}
	},
	methods : {
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.SaveForm({
		    	url: '../../base/trade_log/save?_' + $.now(),
		    	param: vm.tradeLog,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})
