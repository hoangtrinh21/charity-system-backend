package com.charity.hoangtrinh.utils;

import com.google.gson.JsonObject;

import java.time.LocalDate;

public class JsonUtil {
    public static String getString(JsonObject object, String key) {
        return object.get(key) != null ? object.get(key).getAsString() : null;
    }

    public static Integer getInt(JsonObject object, String key) {
        return object.get(key) != null ? object.get(key).getAsInt() : null;
    }

    public static Long getLong(JsonObject object, String key) {
        return object.get(key) != null ? object.get(key).getAsLong() : null;
    }

    public static LocalDate getLocalDate(JsonObject object, String key) {
        return object.get(key) != null ? LocalDate.parse(object.get(key).getAsString()) : null;
    }
}
