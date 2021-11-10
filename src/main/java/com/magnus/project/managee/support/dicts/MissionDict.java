package com.magnus.project.managee.support.dicts;

public enum MissionDict {

    MISSION_ID("missionId"),
    MISSION_STATUS("status"),
    MISSION_TEST_ORDER_ID("orderId"),
    MISSION_PLAN_TEST_FINISH_TIME("planFinishTime");

    public String value;

    MissionDict(String str) {
        value = str;
    }

    public String getValue() {
        return value;
    }
}
