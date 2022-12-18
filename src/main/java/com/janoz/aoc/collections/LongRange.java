package com.janoz.aoc.collections;

import java.util.LongSummaryStatistics;
import java.util.stream.LongStream;

public class LongRange {

    private final long min;//included
    private final long max;//included

    public LongRange(LongSummaryStatistics stats) {
        this.min = stats.getMin();
        this.max = stats.getMax();
    }

    public LongRange(long min, long max) {
        this.min = min;
        this.max = max;
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

    public boolean inRange(long v) {
        return v<=max && v>=min;
    }

    public long getMin() {
        return min;
    }

    public long getMax() {
        return max;
    }
}
