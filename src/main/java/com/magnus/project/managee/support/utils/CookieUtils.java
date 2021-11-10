package com.magnus.project.managee.support.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

    public static String getCookieValueByKey(String key, HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static void clearCookieValueByKey(String key, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    cookie.setMaxAge(0);
                    cookie.setValue(null);
                    httpServletResponse.addCookie(cookie);
                    break;
                }
            }
        }
    }

    public static void setCookie(String key, String value, HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
    }
}
