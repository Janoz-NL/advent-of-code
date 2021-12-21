package com.janoz.aoc.collections;

public class LongTuple {
    private final long l1;
    private final long l2;

    public LongTuple() {
        this.l1 = 0;
        this.l2 = 0;
    }

    public LongTuple(long l1, long l2) {
        this.l1 = l1;
        this.l2 = l2;
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
