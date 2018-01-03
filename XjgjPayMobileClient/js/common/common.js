/**
 * 通用组件类
 */
var common = {
	/**
	 * 获取APP服务URL
	 */
	"getServiceUrl": function(method) {
		return 'http://localhost:8080/api/app/' + method;
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
	}
}