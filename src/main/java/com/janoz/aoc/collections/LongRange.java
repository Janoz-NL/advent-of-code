package com.janoz.aoc.collections;

import java.util.LongSummaryStatistics;
import java.util.Optional;
import java.util.stream.LongStream;

public class LongRange {

    private final long min;//included
    private final long max;//included

    public LongRange(LongSummaryStatistics stats) {
        this.min = stats.getMin();
        this.max = stats.getMax();
    }

    public LongRange(long l1, long l2) {
        this.min = Math.min(l1,l2);
        this.max = Math.max(l1,l2);
    }

    public LongStream stream() {
        return LongStream.rangeClosed(min,max);
    }

    public long size() {
        return max - min + 1;
    }

    public LongRange grow() {
        return new LongRange(min -1, max +1);
    }

    public Optional<LongRange> intersect(LongRange other) {
        if (this.max < other.min || this.min > other.max) {
            return Optional.empty();
        }
        return Optional.of(new LongRange(Math.max(this.min, other.min), Math.min(this.max, other.max)));
    }

    public boolean contains(long v) {
        return v<=max && v>=min;
    }

    public long getMin() {
        return min;
    }

    public long getMax() {
        return max;
    }
}
