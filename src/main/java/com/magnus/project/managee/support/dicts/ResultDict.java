package com.magnus.project.managee.support.dicts;

public enum ResultDict {

    USER_LIST("userList"),
    BUSINESS_LIST("businessList"),
    TOTAL_PAGES("totalPages");

    public String name;

    ResultDict(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }
}
