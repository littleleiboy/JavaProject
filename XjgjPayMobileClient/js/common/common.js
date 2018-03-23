/**
 * 通用组件类
 */
var common = {
	/**
	 * 获取APP服务URL
	 */
	"getServiceUrl": function(method) {
		//return 'https://weidusx.com:8443/api/app/' + method;
		return 'http://1637344my0.imwork.net/api/app/' + method;
	},
	"getDomainUrl": function(pagePath) {
		//return 'https://weidusx.com:8443/' + pagePath;
		return 'http://1637344my0.imwork.net/' + pagePath;
	},
	"localSettingsKey": "local_settings",
	/**
	 * 设置应用本地配置
	 **/
	"setSettings": function(settings) {
		settings = settings || {};
		localStorage.setItem(this.localSettingsKey, JSON.stringify(settings));
	},
	/**
	 * 获取应用本地配置
	 **/
	"getSettings": function() {
		var settingsText = localStorage.getItem(this.localSettingsKey) || "{}";
		return JSON.parse(settingsText);
	},
	/**
	 * 判断是否为空对象
	 */
	"isEmptyObject": function(obj) {
		for(var key in obj) {
			return false;
		}
		return true;
	},
	/**
	 * 判断是否为空值
	 */
	"isNullOrEmpty": function(obj) {
		return(obj == null || obj == "");
	},
	/**
	 * 验证是否为数字
	 */
	"isDigit": function(str) {
		str = str || '';
		return(/^\d+$/.test(str));
	},
	/**
	 * 验证是否为保留两位小数的金额
	 */
	"isMoney": function(str) {
		var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
		return(reg.test(str));
	},
	/**
	 * 通用消息提示语
	 */
	"msg": {
		"msgServerErr": function() {
			return "出错了，服务暂时不可用。";
		}
	},
	/**
	 * 显示元素
	 */
	"show": function(obj) {
		obj.style.display = "block";
	},
	/**
	 * 隐藏元素
	 */
	"hide": function(obj) {
		obj.style.display = "none";
	},
	/**
	 * 以POST方式请求服务器资源
	 * @param {Object} url 请求接口或资源的URL
	 * @param {Object} data JSON类型的数据
	 * @param {Object} success 响应成功的处理函数
	 * @param {Object} [error] 响应失败的处理函数
	 */
	"postJSON": function() {
		var url = arguments[0];
		var data = arguments[1];
		var success = arguments[2];
		var error = arguments[3] || function(xhr, type, errorThrown) {
			console.log(url);
			console.log(type);
			mui.toast(common.msg.msgServerErr());
		}

		mui.ajax(url, {
			data: data,
			dataType: 'json',
			type: 'post',
			timeout: 600000,
			headers: {
				'Content-Type': 'application/json'
			},
			success: success,
			error: error
		});
	}
}