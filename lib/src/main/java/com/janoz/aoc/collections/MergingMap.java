package com.janoz.aoc.collections;

import java.util.HashMap;
import java.util.Map;

public class MergingMap {

    private final Map<Integer, Integer> backingMap = new HashMap<>();

    public int addMapping(final int i1, final int i2) {
        int ai1 = getActual(i1);
        int ai2 = getActual(i2);
        if (ai1 == ai2) return ai1;
        int result = Math.min(ai1, ai2);
        backingMap.put(Math.max(ai1,ai2), result);
        if (result != i1) backingMap.put(i1, result);
        if (result != i2) backingMap.put(i2, result);
        return result;
    }

    public int getActual(int i) {
        if (backingMap.containsKey(i)) {
            return getActual(backingMap.get(i));
        }
        return i;
    }
}
