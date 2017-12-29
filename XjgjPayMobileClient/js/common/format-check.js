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
	this.isDigit = function(str) {
		str = str || '';
		return(/^\d+$/.test(str));
	}

	/**
	 * 验证网址
	 * @param {Object} str_url
	 */
	this.isURL = function(str_url) {
		var strRegex = "^((https|http|ftp|rtsp|mms)?://)" +
			"?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@
			+
			"(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
			+
			"|" // 允许IP和DOMAIN（域名）
			+
			"([0-9a-z_!~*'()-]+\.)*" // 域名- www.
			+
			"([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名
			+
			"[a-z]{2,6})" // first level domain- .com or .museum
			+
			"(:[0-9]{1,4})?" // 端口- :80
			+
			"((/?)|" // a slash isn't required if there is no file name
			+
			"(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
		var re = new RegExp(strRegex);
		return(re.test(str_url));
	}
}