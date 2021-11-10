package com.magnus.project.managee.support.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManageeUtils {



    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
