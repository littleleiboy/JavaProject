(function($, owner) {
	owner.localSettingsKey = 'local_settings';

	owner.login = function(url, loginInfo, callback) {
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
		$.ajax(url, {
			data: loginInfo,
			dataType: 'json',
			type: 'post',
			timeout: 600000,
			success: function(result) {
				if(result.success) {
					return owner.createState(result.data, callback);
				} else {
					return callback(result.msg);
				}
			},
			error: function(xhr, type, errorThrown) {
				console.log(type);
				plus.nativeUI.toast('请检查网络连接，或稍后再试');
			}
		});
	};
	/**
	 * 用户登录
	 **/
	owner.createState = function(loginData, callback) {
		var state = owner.getState();
		state.member_pk = loginData.member_pk;
		state.member_no = loginData.member_no;
		state.member_name = loginData.member_name;
		state.member_mobile = loginData.member_mobile;
		state.access_token = loginData.member_mobile + '_' + loginData.access_token;
		owner.setState(state);
		return callback();
	};

	/**
	 * 获取当前状态
	 **/
	owner.getState = function() {
		var stateText = localStorage.getItem(owner.localSettingsKey) || '{}';
		return JSON.parse(stateText);
	};

	/**
	 * 设置当前状态
	 **/
	owner.setState = function(state) {
		state = state || {};
		localStorage.setItem(owner.localSettingsKey, JSON.stringify(state));
	};

	/**
	 * 获取登录本地配置
	 **/
	owner.setSettings = function(settings) {
		settings = settings || {};
		localStorage.setItem('login_settings', JSON.stringify(settings));
	};

	/**
	 * 设置登录本地配置
	 **/
	owner.getSettings = function() {
		var settingsText = localStorage.getItem('login_settings') || "{}";
		return JSON.parse(settingsText);
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
}(mui, window.app = {}));