package com.jackson.funny.utils;

import com.jackson.utils.L;

import java.util.List;

/**
 * Create by: Jackson
 */
public class HappyLogUtil {


    public static void title(String title) {
        L.console(title);
    }

    public static void content(List<String> content){
        for (String s : content) {
            p(s);
        }
    }


    public static void question(List<String> question) {
        for (String s : question) {
            p(s);
        }
    }


    public static void errerMsg(List<String> errorMsg) {
        for (String s : errorMsg) {
            p(s);
        }
    }




    public static void p(String msg) {
        System.out.println(msg);
    }

    public static void inputError(String errorMsg) {
        System.out.println(errorMsg);
    }


    public static void normal(String msg){
        p(msg);
    }
}
