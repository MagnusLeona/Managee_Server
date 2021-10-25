package com.magnus.project.managee.support.dicts;

public enum ConfigDict {

    DRUID_URL("druid.url"),
    DRUID_USERNAME("druid.username"),
    DRUID_PASSWORD("druid.password"),
    DRUID_DRIVER("druid.driver-class-name"),
    DRUID_MAX_ACTIVE("druid.maxActive"),
    DRUID_MAX_IDLE("druid.maxIdle"),
    DRUID_INITIAL_SIZE("druid.initialSize"),
    MYBATIS_ENVIRONMENT_ID("1");

    public String value;

    ConfigDict(String val) {
        value = val;
    }

    public String getValue() {
        return value;
    }
}
