package com.magnus.project.managee.support.aop.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;

@Aspect
@Component
public class RequestLoggerAspects {

    Logger logger = LogManager.getLogger(RequestLoggerAspects.class);

//    private ThreadLocal<Long> startTime = new ThreadLocal<Long>();

    @Pointcut("execution(* com.magnus.project.managee.work.controller..*.*(..))")
    public void point() {}

    @Around("point()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String requestURI = request.getRequestURI();
        long startTime = System.currentTimeMillis();
        long endTime = 0;
        Object proceed = null;
        logger.info("$$$ Transaction Start $$$ [Uri: " + requestURI + "]  [StartTime: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTime) + "]");
        try {
            proceed = proceedingJoinPoint.proceed();
            endTime = System.currentTimeMillis();
        } catch (Throwable throwable) {
            endTime = System.currentTimeMillis();
            logger.info("$$$ Transaction Error $$$ [Uri: " + requestURI + "]  [ErrorTime: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endTime) + "]");
            logger.error(throwable.getMessage());
            throw throwable;
        }
        logger.info("$$$ Transaction End $$$ [Uri: " + requestURI + "]  [EndTime: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()) + "] [TimeCost: " + (endTime - startTime) + "]");
        return proceed;
    }
}
