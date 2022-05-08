package com.magnus.project.managee.support.aop.aspects;

import com.magnus.project.managee.support.aop.aspects.annotations.LoginRequired;
import com.magnus.project.managee.support.dicts.CommonDict;
import com.magnus.project.managee.support.dicts.UserDict;
import com.magnus.project.managee.support.utils.CookieUtils;
import com.magnus.project.managee.support.utils.ManageeUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LoginCheckAspects {

    @Autowired
    RedisTemplate loginRedisTemplate;

    @Pointcut("execution(* com.magnus.project.managee.work.controller..*.*(..))")
    public void pointCut() {}

    @Before("pointCut()")
    public void beforePointCut(JoinPoint joinPoint) throws Exception {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        LoginRequired annotation = signature.getMethod().getAnnotation(LoginRequired.class);
        if(annotation != null) {
            // 需要登录
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            // 判断是否登录
            String cookieValueByKey = CookieUtils.getCookieValueByKey(UserDict.USER_LOGIN_TOKEN.getStr(), request);
            if(ManageeUtils.isNullOrEmpty(cookieValueByKey) || Boolean.FALSE.equals(loginRedisTemplate.hasKey(cookieValueByKey))) {
                throw new Exception("login required");
            }
        }
    }
}
