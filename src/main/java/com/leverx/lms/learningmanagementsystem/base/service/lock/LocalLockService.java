package com.leverx.lms.learningmanagementsystem.base.service.lock;

import com.leverx.lms.learningmanagementsystem.base.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
public class LocalLockService implements LockService {

    private final Map<String, ReentrantLock> lockMap = new ConcurrentHashMap<>();

    @Override
    public AutoCloseable acquireLock(String key, String errorMessage) {
        ReentrantLock lock = lockMap.computeIfAbsent(key, k -> new ReentrantLock());
        boolean acquired;
        try {
            acquired = lock.tryLock(5, TimeUnit.SECONDS);
            if (!acquired) {
                throw new BaseException("Failed to acquire lock", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            long start = System.nanoTime();
            return () -> {
                lock.unlock();
                long durationMs = (System.nanoTime() - start) / 1_000_000;
                if (durationMs > 1000) {
                    log.warn("[WARN] LocalLockService: Lock for key '{}' was held for {} ms", key, durationMs);
                }
            };
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new BaseException("Interrupted while waiting for lock", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
