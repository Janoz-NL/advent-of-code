package com.janoz.aoc.collections;

import java.util.HashMap;
import java.util.Map;

public class TupleKeyMap<K extends Comparable<K>, V> {

    private final Map<K, Map<K,V>> map = new HashMap<>();

    public V get(K key1, K key2) {
        K low,high;
        if (key1.compareTo(key2) < 0) {
            low = key1;
            high = key2;
        } else {
            high = key1;
            low = key2;
        }
        if (map.containsKey(low)) {
            return map.get(low).get(high);
        } else {
            return null;
        }
    }

    public void put(K key1, K key2, V value) {
        K low,high;
        if (key1.compareTo(key2) < 0) {
            low = key1;
            high = key2;
        } else {
            high = key1;
            low = key2;
        }
        map.putIfAbsent(low, new HashMap<>());
        map.get(low).put(high,value);
    }
}
