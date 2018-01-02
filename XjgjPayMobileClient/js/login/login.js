/**
 * 演示程序当前的 “注册/登录” 等操作，是基于 “本地存储” 完成的
 * 当您要参考这个演示程序进行相关 app 的开发时，
 * 请注意将相关方法调整成 “基于服务端Service” 的实现。
 **/
(function($, owner) {
	/**
	 * 用户登录
	 **/
	owner.login = function(url, loginInfo, localSettingsKey, callback) {
		callback = callback || $.noop;
		loginInfo = loginInfo || {};
		loginInfo.mobileNum = loginInfo.mobileNum || '';
		loginInfo.pass = loginInfo.pass || '';

		if(loginInfo.mobileNum.length == 0) {
			return callback('请输入绑定的手机号');
		}
		if(loginInfo.pass.length == 0) {
			return callback('请输入密码');
		}

		mui.post(url, loginInfo, function(result) {
			if(result.success) {
				return owner.createState(result.data, callback);
			} else {
				return callback(result.msg);
			}
		}, 'json');
	};

	owner.createState = function(loginData, callback) {
		var state = owner.getState();
		state.member_pk = loginData.member_pk;
		state.member_no = loginData.member_no;
		state.member_name = loginData.member_name;
		state.member_mobile = loginData.member_mobile;
		state.access_token = loginData.access_token;
		owner.setState(state);
		return callback();
	};

	/**
	 * 获取当前状态
	 **/
	owner.getState = function() {
		var stateText = localStorage.getItem(localSettingsKey) || '{}';
		return JSON.parse(stateText);
	};

	/**
	 * 设置当前状态
	 **/
	owner.setState = function(state) {
		state = state || {};
		localStorage.setItem(localSettingsKey, JSON.stringify(state));
	};

	/**
	 * 获取登录本地配置
	 **/
	owner.setSettings = function(settings) {
		settings = settings || {};
		localStorage.setItem('login_settings', JSON.stringify(settings));
	}

	/**
	 * 设置登录本地配置
	 **/
	owner.getSettings = function() {
		var settingsText = localStorage.getItem('login_settings') || "{}";
		return JSON.parse(settingsText);
	}

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
}(mui, window.app = {}));