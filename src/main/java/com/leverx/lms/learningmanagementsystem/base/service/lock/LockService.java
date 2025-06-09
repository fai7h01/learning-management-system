package com.leverx.lms.learningmanagementsystem.base.service.lock;

public interface LockService {

    AutoCloseable acquireLock(String key, String errorMessage);
}
