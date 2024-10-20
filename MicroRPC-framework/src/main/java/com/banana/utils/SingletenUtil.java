package com.banana.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SingletenUtil {


    private static final ConcurrentHashMap<Class<?>, Object> singletenMap = new ConcurrentHashMap<>();



    private SingletenUtil() {

    }

    @SuppressWarnings("unchecked")
    public static <T> T getInstance(Class<T> clazz) {
        return (T) singletenMap.computeIfAbsent(clazz, k -> {
            try {
                return k.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        });

    }

}
