package com.janoz.aoc.y2021.day21;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class AlwaysMap<K,V> implements Map<K,V> {

    private Map<K,V> backing = new HashMap<>();

    private Supplier<V> constructor;


    public AlwaysMap(Supplier<V> constructor) {
        this.constructor = constructor;
    }

    @Override
    public V get(Object key) {
        if (!backing.containsKey(key)) {
            backing.put((K)key, constructor.get());
        }
        return backing.get(key);
    }

    @Override
    public int size() {
        return backing.size();
    }

    @Override
    public boolean isEmpty() {
        return backing.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return backing.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return backing.containsValue(value);
    }

    @Override
    public V put(K key, V value) {
        return backing.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return backing.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        backing.putAll(m);
    }

    @Override
    public void clear() {
        backing.clear();
    }

    @Override
    public Set<K> keySet() {
        return backing.keySet();
    }

    @Override
    public Collection<V> values() {
        return backing.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return backing.entrySet();
    }
}
