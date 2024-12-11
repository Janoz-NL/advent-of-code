package com.janoz.aoc.collections;

import java.util.HashMap;
import java.util.function.BiFunction;

public class AccumulatingMap<K,V,X> extends HashMap<K, V> {

    private final BiFunction<K,X,V> supplier;
    private final BiFunction<V,V,V> merger;

    /**
     *
     * @param supplier generates e new value based on some input and the key
     * @param merger Merges the previous value in the map with the new value
     */

    public AccumulatingMap(BiFunction<K,X,V> supplier, BiFunction<V, V, V> merger) {
        this.supplier = supplier;
        this.merger = merger;
    }

    /**
     * Update the value at position key with the value created by the supplier based
     * on key and input. If a value was already available it is merged with the new value.
     *
     * @param key
     * @param input
     */
    public void accumulate(K key, X input) {
        merge(key, supplier.apply(key,input), merger);
    }
}
