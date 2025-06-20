package com.leverx.lms.learningmanagementsystem.base.aspect;

import com.leverx.lms.learningmanagementsystem.base.service.lock.LocalLockService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Aspect
@Component
@RequiredArgsConstructor
public class LockAspect {

    private final Map<String, ReentrantLock> lockMap = new ConcurrentHashMap<>();

    @Around("@annotation(lockAnnotation)")
    public Object around(ProceedingJoinPoint joinPoint, Lock lockAnnotation) throws Throwable {
        String key = lockAnnotation.key();
        long timeout = lockAnnotation.timeout();
        ReentrantLock lock = lockMap.computeIfAbsent(key, k -> new ReentrantLock());

        boolean acquired = false;
        try {
            acquired = lock.tryLock(timeout, TimeUnit.MILLISECONDS);
            if (!acquired) {
                throw new IllegalStateException("Could not acquire lock for key: " + key);
            }
            return joinPoint.proceed();
        } finally {
            if (acquired) {
                lock.unlock();
            }
        }
    }
}
