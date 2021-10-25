package com.magnus.project.managee.support.dicts;

public enum CommonDict {

    PAGE("page"),
    PAGE_SIZE("pageSize");

    public String name;

    CommonDict(String str) {
        name = str;
    }

    public String getName() {
        return name;
    }
}
