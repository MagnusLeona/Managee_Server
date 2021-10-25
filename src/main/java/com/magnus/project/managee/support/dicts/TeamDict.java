package com.magnus.project.managee.support.dicts;

public enum TeamDict {
    AUTO_INCREMENT_ID("id"),
    TEAM_ID("teamId"),
    TEAM_USER_ID("userId"),
    TEAM_LEADERS("teamLeaders"),
    TEAM_MEMBERS("teamMembers"),
    TEAM_ID_LIST("teamIdList"),
    TEAM_USER_ROLE("userRole");

    public String name;

    TeamDict(String str) {
        name = str;
    }

    public String getName() {
        return name;
    }
}
