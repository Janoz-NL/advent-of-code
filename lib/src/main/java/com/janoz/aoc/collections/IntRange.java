package com.janoz.aoc.collections;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.Optional;
import java.util.stream.LongStream;

public class IntRange {

    private final int min;//included
    private final int max;//included

    public IntRange(IntSummaryStatistics stats) {
        this.min = stats.getMin();
        this.max = stats.getMax();
    }

    public IntRange(int... ints) {
        this(Arrays.stream(ints).summaryStatistics());
    }

    public LongStream stream() {
        return LongStream.rangeClosed(min,max);
    }

    public int size() {
        return max - min + 1;
    }

    public IntRange grow() {
        return new IntRange(min -1, max +1);
    }

    public Optional<IntRange> intersect(IntRange other) {
        if (this.max < other.min || this.min > other.max) {
            return Optional.empty();
        }
        return Optional.of(new IntRange(Math.max(this.min, other.min), Math.min(this.max, other.max)));
    }

    public boolean contains(int v) {
        return v<=max && v>=min;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
