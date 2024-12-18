package com.janoz.aoc.collections;

import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A Hashmap that wil always contain the requested key. If the backing hashmap doesn't contain the key
 * a new value will be created and added to the map using the supplied fucntion.
 *
 * @param <K>
 * @param <V>
 */
public class AlwaysHashMap<K,V> extends HashMap<K,V> {

    private final Function<K,V> constructor;

    /**
     * @param constructor constructor of a new element when the map doesn't contain the key.
     */
    public AlwaysHashMap(Supplier<V> constructor) {
        this.constructor = (key) -> constructor.get();
    }

    /**
     * @param constructor function to create a new element based on the key when the map doesn't contain the key.
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
