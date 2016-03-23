package com.hellosliu.easyrecyclerview.utils;

public class StringUtils {
    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNotBlank(String str){
        return !isBlank(str);
    }

    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0) && isBlank(str);
    }
}
