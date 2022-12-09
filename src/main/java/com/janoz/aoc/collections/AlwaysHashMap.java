package com.janoz.aoc.collections;

import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A Hashmap that wil always contain the requested key. If the backing hashmap doesn't contain the key
 * a new value will be added using the supplied constructor.
 *
 *
 * @param <K>
 * @param <V>
 */
public class AlwaysHashMap<K,V> extends HashMap<K,V> {

    private final Function<K,V> constructor;

    /**
     * @param constructor supplier of a new element when a key is requested for the first time.
     */
    public AlwaysHashMap(Supplier<V> constructor) {
        this.constructor = (key) -> constructor.get();
    }

    /**
     * @param constructor supplier of a new element when a key is requested for the first time.
     */
    public AlwaysHashMap(Function<K,V> constructor) {
        this.constructor = constructor;
    }

    @Override
    public V get(Object key) {
        @SuppressWarnings("unchecked")
        K realKey = (K)key;
        if (!super.containsKey(realKey)) {
            super.put(realKey, constructor.apply(realKey) );
        }
        return super.get(key);
    }
}
