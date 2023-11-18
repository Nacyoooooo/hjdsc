package com.chenzhihao.serviceuser.service;

import com.chenzhihao.serviceuser.result.Result;

/**
 * 关于对战的全部方法
 */
public interface PlayService {
    Result<?> requestfight(Long opponentId);

    Result<?> requestStatus();

    Result<?> useSkill(Long skillid);
}
