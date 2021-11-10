package com.magnus.project.managee.work.controller.user;

import com.magnus.project.managee.support.dicts.UserDict;
import com.magnus.project.managee.support.utils.CookieUtils;
import com.magnus.project.managee.support.utils.ManageeUtils;
import com.magnus.project.managee.work.entity.User;
import com.magnus.project.managee.work.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;

@RestController
@PropertySource("classpath:properties/business.properties")
public class UserLogin {

    @Autowired
    RedisTemplate loginRedisTemplate;

    @Autowired
    UserService userService;

    @Value("${login.expire-time}")
    public int loginExpireTime;

    @RequestMapping("/user/login")
    public void userLogin(@RequestBody Map map, @CookieValue(required = false, name = "token") String token, HttpServletResponse httpServletResponse) throws Exception {
        // 登录
        Integer userId = (Integer) map.get(UserDict.USER_ID.getStr());
        String password = (String) map.get(UserDict.USER_PASSWORD.getStr());
        // 判断是否登录是需要依靠Cookies
        // 校验是否已经登录
        if (token != null && !token.isEmpty()) {
            Boolean isLogined = loginRedisTemplate.hasKey(token);
            if (isLogined) {
                // 如果已经登录，则刷新登录超时时间
                loginRedisTemplate.expire(token, Duration.ofMinutes(loginExpireTime));
                return;
            }
        }
        // 校验密码是否正确
        User user = userService.selectUserById(userId);
        if (user == null) {
            throw new Exception("user is not registerd yet");
        }
        if (password != null && password.equals(user.getPassword())) {
            // 密码一致
            String tokenValue = UUID.randomUUID().toString();
            // 将tokenValue存入redis
            loginRedisTemplate.opsForValue().set(tokenValue, user, Duration.ofMinutes(loginExpireTime));
            CookieUtils.setCookie(UserDict.USER_LOGIN_TOKEN.getStr(), tokenValue, httpServletResponse);
        } else {
            throw new Exception("error password or user name");
        }
    }

    @RequestMapping("/user/logout")
    public void userLogout(@CookieValue(required = false, name = "token") String token, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        // 退出登录
        if (!ManageeUtils.isNullOrEmpty(token)) {
            CookieUtils.clearCookieValueByKey(token, httpServletRequest, httpServletResponse);
            loginRedisTemplate.delete(token);
        }
    }
}