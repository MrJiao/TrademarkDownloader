package com.jackson.utils;

import java.util.UUID;

/**
 * Create by: Jackson
 */
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
