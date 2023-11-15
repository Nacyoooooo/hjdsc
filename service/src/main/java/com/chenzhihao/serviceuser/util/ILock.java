package com.chenzhihao.serviceuser.util;

public interface ILock {
    boolean tryLock(long timeoutSec);
    void unlock();
}
