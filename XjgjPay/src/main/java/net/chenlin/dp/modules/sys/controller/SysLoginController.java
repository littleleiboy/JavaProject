package net.chenlin.dp.modules.sys.controller;

import java.io.IOException;

import net.chenlin.dp.modules.sys.entity.SysUserTokenEntity;
import net.chenlin.dp.modules.sys.oauth2.OAuth2Token;
import net.chenlin.dp.modules.sys.oauth2.TokenGenerator;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.chenlin.dp.common.annotation.SysLog;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.common.utils.MD5Utils;
import net.chenlin.dp.common.utils.ShiroUtils;
import net.chenlin.dp.modules.sys.entity.SysUserEntity;
import net.chenlin.dp.modules.sys.service.SysUserService;

/**
 * 用户controller
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 * @url www.chenlintech.com
 * @date 2017年8月8日 下午2:48:50
 */
@RestController
@RequestMapping("/sys")
public class SysLoginController extends AbstractController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 登录
     */
    @SysLog("登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public R login(String username, String password) throws IOException {
        SysUserEntity user = sysUserService.getByUserName(username);
        password = MD5Utils.encrypt(username, password);

        if (user == null || !user.getPassword().equals(password)) {
            return R.error("用户名或密码错误");
        }

        if (user.getStatus() == 0) {
            return R.error("账号已被锁定,请联系管理员");
        }

        R r = sysUserService.saveUserToken(user.getUserId());

        try {
            Subject subject = ShiroUtils.getSubject();
            String token = String.valueOf(r.get("token"));
            OAuth2Token oAuth2Token = new OAuth2Token(token);
            subject.login(oAuth2Token);
        } catch (UnknownAccountException e) {
            return R.error(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return R.error(e.getMessage());
        } catch (LockedAccountException e) {
            return R.error(e.getMessage());
        } catch (AuthenticationException e) {
            return R.error("账户验证失败；" + e.getMessage());
        }

        return r;
    }

    /**
     * 退出
     */
    @SysLog("退出系统")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public R logout() {
        R r = sysUserService.updateUserToken(getUserId());
        ShiroUtils.logout();
        return r;
    }

}
