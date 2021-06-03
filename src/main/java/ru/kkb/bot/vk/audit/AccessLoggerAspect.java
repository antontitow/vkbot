package ru.kkb.bot.vk.audit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Marchenko_DS in 03/06/2021 - 18:22
 */
@Log
@Aspect
public class AccessLoggerAspect {

    //  private ObjectMapper objectMapper = new ObjectMapper();

    @Pointcut("execution(* ru.kkb.bot.vk.controller.*.*(..)) || " +
            "execution(* ru.kkb.bot.vk.controller.*.*.*(..))")
    public void controllerMethod() {
    }

    @SneakyThrows
    @Before("controllerMethod() || @annotation(AuditLog)")
    public void proceedAudit(JoinPoint joinPoint) {

        final HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Map<String, String> data = new HashMap<>();
        Method method = MethodSignature.class.cast(joinPoint.getSignature()).getMethod();
        Class type = joinPoint.getSignature().getDeclaringType();
        data.put(" Calling ", type.getSimpleName() + "::" + method.getName() + "()");

        if (null != request) {
            data.put("Method Type : ", request.getMethod());
            Enumeration headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = (String) headerNames.nextElement();
                String headerValue = request.getHeader(headerName);
                data.put("Header Name: ", headerName + " Header Value : " + headerValue);
            }
            data.put("Request Path info :", request.getServletPath());
        }
        log.info(Joiner.on(",").withKeyValueSeparator(" - ").join(data));
    }


    @Around("controllerMethod() || @annotation(AuditLog)")
    public void logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        try {
            Map<String, String> data = new HashMap<>();
            Method method = MethodSignature.class.cast(joinPoint.getSignature()).getMethod();
            Class type = joinPoint.getSignature().getDeclaringType();
            Object result = joinPoint.proceed();
            long elapsedTime = System.currentTimeMillis() - start;
            data.put("Method ", type.getSimpleName() + "::" + method.getName() + "()");
            data.put(" execution time : ", elapsedTime + " ms");

            log.info(Joiner.on(",").withKeyValueSeparator(" - ").join(data));
        } catch (IllegalArgumentException e) {
            log.info("Illegal argument " + Arrays.toString(joinPoint.getArgs()) + " in "
                    + joinPoint.getSignature().getName() + "()");
            throw e;
        }
    }

    @AfterReturning(pointcut = "controllerMethod() || @annotation(AuditLog)", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        String returnValue = this.getValue(result);
        log.info("Method Return value : " + returnValue);
    }

    @AfterThrowing(pointcut = "controllerMethod() || @annotation(AuditLog)", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        log.warning("An exception has been thrown in " + joinPoint.getSignature().getName() + " ()" + " Cause : " + exception.getCause());
    }


    private String getValue(Object result) {
        String returnValue = null;
        if (null != result) {
            if (result.toString().endsWith("@" + Integer.toHexString(result.hashCode()))) {
                returnValue = result.toString();
            } else {
                returnValue = result.toString();
            }
        }
        return returnValue;
    }
}
