package com.magnus.project.managee.support.aop.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;

@Aspect
@Component
public class RequestLoggerAspects {

    Logger logger = LogManager.getLogger(RequestLoggerAspects.class);

    private ThreadLocal<Long> startTime = new ThreadLocal<Long>();

    @Pointcut("execution(* com.magnus.project.managee.work.controller..*.*(..))")
    public void point(){};

    @Before("point()")
    public void before(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String requestURI = request.getRequestURI();
        startTime.set(System.currentTimeMillis());
        logger.info("$$$ Transaction Start $$$ [Uri: " + requestURI + "]  [StartTime: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTime.get()) +"]");
    }

    @After("point()")
    public void after(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String requestURI = request.getRequestURI();
        long endTime = System.currentTimeMillis();
        logger.info("$$$ Transaction End $$$ [Uri: " + requestURI + "]  [EndTime: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endTime) + "] [TimeCost: " + (endTime - startTime.get()) + "]");
        startTime.remove();
    }
}
