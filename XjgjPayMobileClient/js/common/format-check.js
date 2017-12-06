/**
 * 表单输入格式验证类
 * @author Andy Wang
 * @date 2017-12-1
 */
var FormatCheck = function() {
	/**
	 * 验证手机号格式
	 */
	this.isMobileNum = function(str) {
		str = str || '';
		return(/^1[34578]\d{9}$/.test(str));
	}
	/**
	 * 验证邮箱格式
	 */
	this.isEmail = function(email) {
		str = str || '';
		return(/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(str));
	}
	/**
	 * 验证[0-9]数字
	 */
	function isDigits(str) {
		str = str || '';
		return (/^\d+$/.test(str));
	}
}