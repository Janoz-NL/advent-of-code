package com.janoz.aoc.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class SetAsMap<T> implements Map<T, Boolean> {

    private static final Set<Boolean> values = Collections.singleton(true);
    private final Set<T> content;

    public SetAsMap(Set<T> content) {
        this.content = content;
    }

    @Override
    public int size() {
        return content.size();
    }

    @Override
    public boolean isEmpty() {
        return content.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return content.contains(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return values.contains(value);
    }

    @Override
    public Boolean get(Object key) {
        return content.contains(key);
    }

    @Override
    public Boolean put(T key, Boolean value) {
        if (Boolean.TRUE.equals(value)) {
            content.add(key);
        } else {
            content.remove(key);
        }
        return false;
    }

    @Override
    public Boolean remove(Object key) {
        return content.remove(key);
    }

    @Override
    public void putAll(Map<? extends T, ? extends Boolean> m) {
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        content.clear();
    }

    @Override
    public Set<T> keySet() {
        return content;
    }

    @Override
    public Collection<Boolean> values() {
        return values;
    }

    @Override
    public Set<Entry<T, Boolean>> entrySet() {
        throw new UnsupportedOperationException();
    }
}
