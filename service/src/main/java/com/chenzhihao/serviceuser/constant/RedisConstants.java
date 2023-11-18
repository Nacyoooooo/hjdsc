package com.chenzhihao.serviceuser.constant;

public class RedisConstants {
    public static final String LOGIN_USER_KEY="login:user:";
    public static final String LOGIN_ADMIN_KEY="login:admins:";
    public static final String PETS_CONFIG_KEY="pets:config:";
    public static final String PETS_BAG_KEY="pets:bag:";
    public static final String PLAY_STATUS_KEY="play:status:";
    public static final String PLAY_SKILL_KEY="play:skill:";
    public static final String PLAY_FIGHT_KEY="play:fight:";
    public static final Long LOGIN_USER_TTL = 36000L;
    public static final Long PETS_CONFIG_TTL = 36000L;
}
