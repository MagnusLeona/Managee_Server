package com.magnus.project.managee.support.dicts;

public enum BusinessDict {
    AUTO_INCREMENT_ID("id"),
    BUSINESS_ID("businessId"),
    BUSINESS_STATUS("businessStatus"),
    BUSINESS_DEVELOPER("developer"),   // 需求开发人员
    BUSINESS_MANAGER("manager"), // 需求总负责人
    BUSINESS_COUNTER("counter"),  // 需求业务
    BUSINESS_TRACER("tracer"),  // 需求追踪人员
    BUSINESS_USER_ROLE("userRole"),
    BUSINESS_CREATE_TIME("createTime"),
    BUSINESS_EVALUATE_FEEDBACK("feedback"),
    BUSINESS_EVALUATE_PROBLEM("problem"),
    BUSINESS_EVALUATE_START_TIME("startTime"),
    BUSINESS_EVALUATE_COMMIT_TEST_TIME("testCommitTime"),
    BUSINESS_EVALUATE_FINISH_TEST_TIME("testFinishedTime"),
    BUSINESS_EVALUATE_PRODUCT_TIME("productTime"),
    BUSINESS_PROJECT_ID("projectId"),
    BUSINESS_TEST_ID("testId")
    ;

    public String str;

    BusinessDict(String string) {
        str = string;
    }

    public String getStr() {
        return str;
    }
}
