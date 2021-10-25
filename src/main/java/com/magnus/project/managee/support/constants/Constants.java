package com.magnus.project.managee.support.constants;

public class Constants {
    public static final int DEFAULT_INTEGER_ZERO = 0;
    // 默认分页规则
    public static final int DEFAULT_PAGE = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;

    public static final int BUSINESS_STATUS_CREATE = 1;  // 需求状态-创建完成
    public static final int BUSINESS_STATUS_ASSIGN = 2;  // 需求状态-指派中
    public static final int BUSINESS_STATUS_PRE_EVALUATE = 4; // 需求状态-预评估
    public static final int BUSINESS_STATUS_PRE_EVALUATE_PROBLEM = 5;  // 需求状态-预评估异常
    public static final int BUSINESS_STATUS_PRE_EVALUATE_PROBLEM_RESOLVED = 6;  // 需求状态-预评估异常已反馈
    public static final int BUSINESS_STATUS_PRE_EVALUATE_FINISHED = 8; // 需求状态-预评估完成
    public static final int BUSINESS_STATUS_EVALUATE = 9;  // 需求状态-正式评估
    public static final int BUSINESS_STATUS_EVALUATE_FINISHED = 12; // 需求状态-正式评估完成
    public static final int BUSINESS_STATUS_DEVELOP = 14; // 需求状态-开发中
    public static final int BUSINESS_STATUS_TEST = 16; // 需求状态-测试中
    public static final int BUSINESS_STATUS_PRE_PRODUCT = 20; // 需求状态-待上线
    public static final int BUSINESS_STATUS_VERIFICATE = 22; // 需求状态-上线验证中
    public static final int BUSINESS_STATUS_VERIFICATE_FINISHED = 23; // 需求状态-已交付/验证通过
    public static final int BISINESS_STATUS_ARCHIVED = 30; // 需求状态-归档

    public static final int BUSINESS_USER_ROLE_COUNTER = 1;  // 用户在需求中的角色--业务
    public static final int BUSINESS_USER_ROLE_MANAGER = 2; // 用户在需求中的角色--总负责
    public static final int BUSINESS_USER_ROLE_DEVOLOPER = 3;  // 用户在需求中的角色--开发
    public static final int BUSINESS_USER_ROLE_TEST = 4;  // 用户在需求中的角色--测试
    public static final int BUSINESS_USER_ROLE_EVALUATOR = 5; // 用户在需求中的角色--评估人员（一般评估人员这个角色属于临时角色，在评估完成后就需要删除这个关系）
    public static final int BUSINESS_USER_ROLE_SUPPORT_MANAGER = 6;  // 用户在需求中的角色--协负责人
    public static final int BUSINESS_USER_ROLE_TRACER = 7;  // 用户在需求中的角色--跟踪

    public static final int TEAM_USER_ROLE_LEADER = 0;
    public static final int TEAM_USER_ROLE_MEMBER = 2;

    public static final int BUSIENSS_TEAM_ROLE_MANAGER = 1;    // 团队在需求中的角色--总负责
    public static final int BUSINESS_TEAM_ROLE_TRACER = 2;   // 团队在需求中的角色--跟踪
    public static final int BUSINESS_TEAM_ROLE_SUPPORTER = 3;   // 团队在需求中的角色--支持

    public static final int BUSINESS_PRE_EVALUATE_STATUS_ASSIGNED = 0;  // 需求预评估状态--待预评估
    public static final int BUSINESS_PRE_EVALUATE_STATUS_PROBLEM = 1;  // 需求预评估状态--预评估发现问题，需要反馈
    public static final int BUSINESS_PRE_EVALUATE_STATUS_FEEDBACK = 2;  // 需求预评估状态--预评估问题结果已反馈
    public static final int BUSINESS_PRE_EVALUATE_STATUS_FINISHED = 3; //  需求预评估状态--预评估完成
    public static final int BUSINESS_PRE_EVALUATE_STATUS_DECLINE = 4; // 需求预评估状态-拒绝

    public static final int BUSINESS_EVALUATE_START = 0;
    public static final int BUSINESS_EVALUATE_FINISED = 1;

}
