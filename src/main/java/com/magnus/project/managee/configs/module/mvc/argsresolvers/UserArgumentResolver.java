package com.magnus.project.managee.configs.module.mvc.argsresolvers;

import com.magnus.project.managee.support.dicts.CommonDict;
import com.magnus.project.managee.support.dicts.UserDict;
import com.magnus.project.managee.support.utils.CookieUtils;
import com.magnus.project.managee.support.utils.ManageeUtils;
import com.magnus.project.managee.work.entity.User;
import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private RedisTemplate redisTemplate;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 判断处理条件
        if (parameter.getParameterType().isAssignableFrom(User.class)) {
            // 是User类型的就执行注入
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 怎么注入？
        HttpServletRequest nativeRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        String cookieValueByKey = CookieUtils.getCookieValueByKey(UserDict.USER_LOGIN_TOKEN.getStr(), nativeRequest);
        if (ManageeUtils.isNullOrEmpty(cookieValueByKey)) {
            throw new Exception("login required");
        }
        if (redisTemplate.hasKey(cookieValueByKey)) {
            User user = (User) redisTemplate.opsForValue().get(cookieValueByKey);
            return user;
        }
        return null;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
