package com.janoz.aoc.collections;

import java.util.Arrays;

public class LongTuple {
    private final long l1;
    private final long l2;

    public LongTuple() {
        this.l1 = 0;
        this.l2 = 0;
    }

    public LongTuple(String input) {
        this(input,"\\s+");
    }

    public LongTuple(String input, String regex) {
        this(Arrays.stream(input.split(regex)).mapToLong(Long::parseLong).toArray());
    }

    public LongTuple(long... l) {
        this.l1 = l[0];
        this.l2 = l[1];
    }
    public long getLeft() {
        return l1;
    }

    public long getRight() {
        return l2;
    }

    public LongTuple incLeft(int amount) {
        return new LongTuple(l1 + amount, l2);
    }

    public LongTuple switched() {
        return new LongTuple(l2, l1);
    }

    public LongTuple add(LongTuple lt) {
        return new LongTuple(l1 + lt.l1, l2 + lt.l2);
    }

    public LongTuple times(int i) {
        return new LongTuple(l1 * i, l2 * i);
    }
}
