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