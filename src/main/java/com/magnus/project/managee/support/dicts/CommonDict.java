package com.magnus.project.managee.support.dicts;

public enum CommonDict {

    PAGE("page"),
    PAGE_SIZE("pageSize"),
    REDIS_TEMPLATE_SQLCACHE("cacheRedisTemplate"),
    REDIS_TEMPLATE_LOGIN("loginRedisTemplate"),
    REDIS_TEMPLATE_COMMON("commonRedisTemplate")
    ;

    public String name;

    CommonDict(String str) {
        name = str;
    }

    public String getName() {
        return name;
    }
}
