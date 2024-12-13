package com.janoz.aoc.collections;

import java.util.HashMap;
import java.util.Map;

public class MergingMap {

    private final Map<Integer, Integer> backingMap = new HashMap<>();

    public int addMapping(int i1, int i2) {
        i1 = getActual(i1);
        i2 = getActual(i2);
        if (i1 == i2) return i1;
        int result = Math.min(i1, i2);
        backingMap.put(Math.max(i1,i2), result);
        return result;
    }

    public int getActual(int i) {
        if (backingMap.containsKey(i)) {
            return getActual(backingMap.get(i));
        }
        return i;
    }
}
