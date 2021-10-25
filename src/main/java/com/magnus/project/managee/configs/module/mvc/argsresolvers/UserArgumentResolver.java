package com.magnus.project.managee.configs.module.mvc.argsresolvers;

import com.magnus.project.managee.work.entity.User;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

public class UserArgumentResolver implements HandlerMethodArgumentResolver {
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

        HttpServletRequest nativeRequest = (HttpServletRequest) webRequest.getNativeRequest(HttpServletRequest.class);
        Map<String, String[]> parameterMap1 = webRequest.getParameterMap();
        Iterator<String> parameterNames = webRequest.getParameterNames();
        Map<String, String[]> parameterMap = nativeRequest.getParameterMap();
        return null;
    }
}
