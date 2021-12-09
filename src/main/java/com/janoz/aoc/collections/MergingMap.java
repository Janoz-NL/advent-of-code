package com.janoz.aoc.collections;

import java.util.HashMap;
import java.util.Map;

public class MergingMap {

    private Map<Integer, Integer> backingMap = new HashMap<>();

    public void addMapping(int i1, int i2) {
        i1 = getActual(i1);
        i2 = getActual(i2);
        if (i1 == i2) return;
        backingMap.put(Math.max(i1,i2),Math.min(i1,i2));
    }

    public int getActual(int i) {
        if (backingMap.containsKey(i)) {
            return getActual(backingMap.get(i));
        }
        return i;
    }
}
