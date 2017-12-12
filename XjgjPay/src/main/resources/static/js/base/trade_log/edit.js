/**
 * 编辑-交易记录js
 */
var vm = new Vue({
	el:'#dpLTE',
	data: {
		tradeLog: {
			id: 0
		}
	},
	methods : {
		setForm: function() {
			$.SetForm({
				url: '../../base/trade_log/info?_' + $.now(),
		    	param: vm.tradeLog.id,
		    	success: function(data) {
		    		vm.tradeLog = data;
		    	}
			});
		},
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.ConfirmForm({
		    	url: '../../base/trade_log/update?_' + $.now(),
		    	param: vm.tradeLog,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})