package com.robl2e.thistimes.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by robl2e on 9/17/17.
 */

public class JsonUtils {
    public static String toJson(Object data) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.toJson(data);
    }

    public static <T> T fromJson(String jsonString, Class<T> type) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.fromJson(jsonString, type);
    }
}
