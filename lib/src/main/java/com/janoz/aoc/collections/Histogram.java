package com.janoz.aoc.collections;

import com.janoz.aoc.math.Operations;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

public class Histogram<K, N extends Number & Comparable<N>> {

    private final Map<K,N> backingMap = new HashMap<>();
    private final Operations<N> operations;


    private Histogram(Operations<N> ops) {
        this.operations = ops;
    }

    public void inc(K key) {
        inc(key,operations.one.get());
    }

    public void inc(K key, N amount) {
        backingMap.put(key, operations.add.apply(get(key), amount));
    }

    public N get(K key) {
        return backingMap.getOrDefault(key, operations.zero.get());
    }

    public Stream<N> streamAmounts() {
        return backingMap.values().stream();
    }

    public Set<Map.Entry<K,N>> entrySet() {
        return backingMap.entrySet();
    }

    /**
     * @param comparatorWhenAmountEqual comperator to sort keys when they have the same ammount. Because
     *                                  the sortorder is reversed (biggest first) the comperator is also
     *                                  applied reversed. Biggest first! (default comparators sort from
     *                                  small to big)
     * @return a sorted list of keys, with the most frequent first.
     */
    public List<K> sorted(Comparator<K> comparatorWhenAmountEqual) {
        return sortedStream(comparatorWhenAmountEqual).toList();
    }

    public Stream<K> sortedStream(Comparator<K> comparatorWhenAmountEqual) {
        TreeSet<K> result = new TreeSet<>((o1, o2) -> {
            int comp = backingMap.get(o2).compareTo(backingMap.get(o1));
            if (comp == 0) {
                return comparatorWhenAmountEqual.compare(o2,o1);
            }
            return comp;
        });
        result.addAll(backingMap.keySet());
        return result.stream();
    }


    public static <K> Histogram<K, Long> longHistogram() {
        return new Histogram<>(Operations.longOperations());
    }

    public static <K> Histogram<K, BigInteger> hugeHistogram() {
        return new Histogram<>(Operations.bigIntegerOperations());
    }
}
