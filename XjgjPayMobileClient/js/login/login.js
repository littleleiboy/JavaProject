/**
 * 演示程序当前的 “注册/登录” 等操作，是基于 “本地存储” 完成的
 * 当您要参考这个演示程序进行相关 app 的开发时，
 * 请注意将相关方法调整成 “基于服务端Service” 的实现。
 **/
(function($, owner) {
	/**
	 * 用户登录
	 **/
	owner.login = function(loginInfo, callback) {
		callback = callback || $.noop;
		loginInfo = loginInfo || {};
		loginInfo.mobile = loginInfo.mobile || '';
		loginInfo.pwd = loginInfo.pwd || '';
		loginInfo.token = loginInfo.token || '';
		if(!checkMobileNum(loginInfo.mobile)) {
			return callback('手机号格式不正确');
		}
		if(loginInfo.pwd.length < 6) {
			return callback('密码最短为 6 个字符');
		}

		//TODO 请求登录验证
		var authed = false;

		if(authed) {
			return owner.createState(loginInfo, callback);
		} else {
			return callback('用户名或密码错误');
		}
	};

	owner.createState = function(loginInfo, callback) {
		var state = owner.getState();
		state.mobile = loginInfo.mobile;
		state.token = loginInfo.token;
		owner.setState(state);
		return callback();
	};

	/**
	 * 获取当前状态
	 **/
	owner.getState = function() {
		var stateText = localStorage.getItem('local_state') || '{}';
		return JSON.parse(stateText);
	};

	/**
	 * 设置当前状态
	 **/
	owner.setState = function(state) {
		state = state || {};
		localStorage.setItem('local_state', JSON.stringify(state));
	};

	/**
	 * 新用户注册
	 **/
	owner.reg = function(regInfo, callback) {
		callback = callback || $.noop;
		regInfo = regInfo || {};
		regInfo.memberName = regInfo.memberName || '';
		regInfo.pwd = regInfo.pwd || '';
		regInfo.idCard = regInfo.idCard || '';
		regInfo.email = regInfo.email || '';
		regInfo.toAddr = regInfo.toAddr || '';
		if(regInfo.memberName == null || regInfo.memberName == '') {
			return callback('请填写姓名');
		}
		if(regInfo.idCard == null || regInfo == '') {
			return callback('请填写身份证号码');
		}
		if(!checkPasswordStr(regInfo.pwd)) {
			return callback('密码为 6 位数字');
		}
		if(!checkEmail(regInfo.email)) {
			return callback('邮箱地址不合法');
		}
		if(regInfo.toAddr == null || regInfo.toAddr == '') {
			return callback('请填写商品去向地址');
		}

		//TODO 提交注册信息

		return callback();
	};

	var checkEmail = function(str) {
		str = str || '';
		//return(str.length > 3 && str.indexOf('@') > -1);
		return(/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(str));
	};

	var checkMobileNum = function(str) {
		str = str || '';
		return(/^1[34578]\d{9}$/.test(str));
	};

	var checkPasswordStr = function(str) {
		str = str || '';
		return(/^\d+$/.test(str)) && str.length == 6;
	};

	/**
	 * 找回密码
	 **/
	owner.forgetPassword = function(email, callback) {
		callback = callback || $.noop;
		if(!checkEmail(email)) {
			return callback('邮箱地址不合法');
		}
		return callback(null, '新的随机密码已经发送到您的邮箱，请查收邮件。');
	};

	/**
	 * 获取应用本地配置
	 **/
	owner.setSettings = function(settings) {
		settings = settings || {};
		localStorage.setItem('local_settings', JSON.stringify(settings));
	}

	/**
	 * 设置应用本地配置
	 **/
	owner.getSettings = function() {
		var settingsText = localStorage.getItem('local_settings') || "{}";
		return JSON.parse(settingsText);
	}
}(mui, window.app = {}));