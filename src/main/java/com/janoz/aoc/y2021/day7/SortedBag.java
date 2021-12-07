package com.janoz.aoc.y2021.day7;

import com.janoz.aoc.Operations;

import java.math.BigInteger;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class SortedBag<V extends Number & Comparable<V>, I extends Number & Comparable<I>> {

    final private SortedMap<V, I> backing = new TreeMap<>();
    final private Operations<V> valueOperations;
    final private Operations<I> indexOperations;
    private I size;
    private V sum;

    private SortedBag(Operations<V> valueOperations, Operations<I> indexOperations) {
        this.valueOperations = valueOperations;
        this.indexOperations = indexOperations;
        sum = valueOperations.zero.get();
        size = indexOperations.zero.get();
    }

    public void put(V item) {
        backing.put(item, indexOperations.add.apply(indexOperations.one.get(),backing.getOrDefault(item, indexOperations.zero.get())));
        size = indexOperations.add.apply(indexOperations.one.get(), size);
        sum = valueOperations.add.apply(sum,item);
    }

    public I size() {
        return size;
    }

    public V sum() {
        return sum;
    }

    public V min() {
        return backing.firstKey();
    }

    public V max() {
        return backing.lastKey();
    }

    public V med() {
        return get(indexOperations.div.apply(size, indexOperations.fromLong.apply(2)));
    }

    public V get(I index) {
        I current = indexOperations.zero.get();
        for (Map.Entry<V, I> entry : backing.entrySet()) {
            current = indexOperations.add.apply(entry.getValue(), current);
            if (current.compareTo(index) > 0) {
                return entry.getKey();
            }
        }
        throw new IndexOutOfBoundsException(index + " bigger than " + size);
    }

    public Iterable<Map.Entry<V, I>> histoEntrySet() {
        return backing.entrySet();
    }

    public static SortedBag<BigInteger,BigInteger> bigIntegerSortedBag() {
        return new SortedBag<>(Operations.bigIntegerOperations(),Operations.bigIntegerOperations());
    }

    public static SortedBag<Long,Long> longSortedBag() {
        return new SortedBag<>(Operations.longOperations(),Operations.longOperations());
    }

}
