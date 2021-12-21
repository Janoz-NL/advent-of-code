package com.janoz.aoc.y2021.day21;

class LongTuple {
    final long l1;
    final long l2;

    public LongTuple() {
        this.l1 = 0;
        this.l2 = 0;
    }

    public LongTuple(long l1, long l2) {
        this.l1 = l1;
        this.l2 = l2;
    }

    LongTuple incLeft(int amount) {
        return new LongTuple(l1 + amount, l2);
    }

    LongTuple switched() {
        return new LongTuple(l2, l1);
    }

    LongTuple add(LongTuple lt) {
        return new LongTuple(l1 + lt.l1, l2 + lt.l2);
    }

    LongTuple times(int i) {
        return new LongTuple(l1 * i, l2 * i);
    }
}
