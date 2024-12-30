package com.janoz.aoc.collections;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class TupleKeyMap<K extends Comparable<K>, V> {

    private final Map<K, Map<K,V>> map = new HashMap<>();

    public V get(K key1, K key2) {
        K[] keys = sortkeys(key1,key2);
        return get(keys);
    }

    private V get(K[] keys) {
        if (map.containsKey(keys[0])) {
            return map.get(keys[0]).get(keys[1]);
        } else {
            return null;
        }
    }

    public boolean contains(K key1, K key2) {
        return contains(sortkeys(key1, key2));
    }

    private boolean contains(K[] keys) {
        return map.containsKey(keys[0]) && map.get(keys[0]).containsKey(keys[1]);
    }

    public void put(K key1, K key2, V value) {
        put(sortkeys(key1,key2), value);
    }

    private void put(K[] keys, V value) {
        map.putIfAbsent(keys[0], new HashMap<>());
        map.get(keys[0]).put(keys[1],value);
    }

    public void merge(K key1, K key2, V value, BiFunction<V,V,V> mergeFunction) {
        K[] keys = sortkeys(key1,key2);
        if (contains(keys)) {
            value = mergeFunction.apply(value, get(keys));
        }
        put(keys, value);
    }

    private K[]  sortkeys(K key1, K key2) {
        K[] result = (K[])new Comparable<?>[2];
        if (key1.compareTo(key2) < 0) {
            result[0] = key1;
            result[1] = key2;
        } else {
            result[0] = key2;
            result[1] = key1;
        }
        return result;
    }
}
