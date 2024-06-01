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

    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> leftIn = new ArrayList<>();
        List<int[]> rightIn = new ArrayList<>();

        int left = newInterval[0], right = newInterval[1];
        for(int[] interval : intervals) {
            if(interval[1] < left) {
                leftIn.add(interval);
            } else if(interval[0] > right) {
                rightIn.add(interval);
            } else {
                left = Math.min(left, interval[0]);
                right = Math.max(right, interval[1]);
            }
        }
        leftIn.add(new int[]{left, right});
        leftIn.addAll(rightIn);
        return leftIn.toArray(new int[leftIn.size()][2]);
    }

}
