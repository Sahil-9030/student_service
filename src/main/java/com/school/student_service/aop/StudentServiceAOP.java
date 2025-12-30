package com.school.student_service.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class StudentServiceAOP {

    private static final Logger logger =
            LoggerFactory.getLogger(StudentServiceAOP.class);

    @Around("execution(* com.school.student_service..*(..))")
    public Object logMethodDetails(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        String methodName = joinPoint.getSignature().toShortString();
        Object[] arguments = joinPoint.getArgs();

        logger.info("Method Called: {}", methodName);
        logger.info("Arguments: {}", Arrays.toString(arguments));

        Object result = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - startTime;

        logger.info("Method Completed: {}", methodName);
        logger.info("Execution Time: {} ms", executionTime);

        return result;
    }
}

