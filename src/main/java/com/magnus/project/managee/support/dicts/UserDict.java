package com.magnus.project.managee.support.dicts;

public enum UserDict {

    USER_ID("userId"),
    USER_ID_LIST("userIdList");

    public String str;

    UserDict(String string) {
        str = string;
    }

    public String getStr() {
        return str;
    }
}
