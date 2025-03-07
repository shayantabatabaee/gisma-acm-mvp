package com.gisma.competition.acm.util;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;

@Aspect
@Component
public class LoggingAspect {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerMethods() {
    }

    @Around("controllerMethods()")
    public Object logRequestAndResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        Class<?> clazz = joinPoint.getTarget().getClass();
        Logger logger = LoggerFactory.getLogger(clazz);

        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes == null) {
            return joinPoint.proceed();
        }

        HttpServletRequest request = attributes.getRequest();
        long startTime = System.currentTimeMillis();

        logger.info("Incoming Request: {} {}", request.getMethod(), request.getRequestURI());
        logger.info("Headers: {}", getHeaders(request));
        logger.info("Request Params: {}", request.getQueryString());
        logger.info("Request Body: {}", getRequestBody(joinPoint));

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            logger.error("Exception: {}", e.getMessage(), e);
            throw e;
        }

        long duration = System.currentTimeMillis() - startTime;

        logger.info("Duration={}ms, Response: {}", duration, result);

        return result;
    }

    private String getHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        StringBuilder headers = new StringBuilder();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            headers.append(header).append("=").append(request.getHeader(header)).append("; ");
        }
        return headers.toString();
    }

    private String getRequestBody(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        return (args != null && args.length > 0) ? (args.length > 1 ? args[1].toString() : args[0].toString()) : "No Body";
    }
}
