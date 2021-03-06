package ru.mirea.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by master on 28.11.2016.
 */
public class JsonUtil {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T fromJson(String json, Class<T> cls) {
        return gson.fromJson(json, cls);
    }
}
