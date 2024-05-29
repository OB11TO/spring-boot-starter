package ru.ob11to.springapp.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.ob11to.springapp.service.ExecutionTimeMethodService;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class TimeTrackingAspect {

    private final ExecutionTimeMethodService executionTimeMethodService;

    @Pointcut("@annotation(ru.ob11to.springapp.annotation.TrackTime)")
    public void hasTrackTime() {
    }

    @Pointcut("@annotation(ru.ob11to.springapp.annotation.TrackAsyncTime)")
    public void hasTrackAsyncTime() {
    }

    @Around("hasTrackTime()")
    public Object addTrackTimeForMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTimeMethod = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long timeTaken = System.currentTimeMillis() - startTimeMethod;
        log.info("Method : {} executed synchronously in {} ms", joinPoint.getSignature(), timeTaken);
        executionTimeMethodService.saveExecutionTime(joinPoint.getSignature().toString(), timeTaken);
        return result;
    }

    @Around("hasTrackAsyncTime()")
    public Object addTrackAsyncTimeForMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTimeMethod = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long timeTaken = System.currentTimeMillis() - startTimeMethod;
        log.info("Method : {} executed asynchronously in {} ms", joinPoint.getSignature(), timeTaken);
        executionTimeMethodService.saveExecutionTime(joinPoint.getSignature().toString(), timeTaken);
        return result;
    }

}
