package com.example.togepi.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class HelloAspect {

    @Pointcut("@annotation(Hello) && execution(public * *(..))")
    private void getHelloPointCut() {
    }

    @Before("getHelloPointCut()")
    public void getHelloBeforeAdvice() {
        log.info("Hello before");
    }

    @After("getHelloPointCut()")
    public void getHelloAfterAdvice() {
        log.info("Hello after");
    }

    @Around("getHelloPointCut()")
    public Object getHelloAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            log.info("Hello around");
            return proceedingJoinPoint.proceed();
        } catch (Exception e) {
            throw e;
        }
    }
}
