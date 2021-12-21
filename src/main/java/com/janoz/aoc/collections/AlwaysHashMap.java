package com.janoz.aoc.collections;

import java.util.HashMap;
import java.util.function.Supplier;

public class AlwaysHashMap<K,V> extends HashMap<K,V> {

    private final Supplier<V> constructor;

    public AlwaysHashMap(Supplier<V> constructor) {
        this.constructor = constructor;
    }

    @Override
    public V get(Object key) {
        @SuppressWarnings("unchecked")
        K realKey = (K)key;
        if (!super.containsKey(realKey)) {
            super.put(realKey, constructor.get());
        }
        return super.get(key);
    }
}
