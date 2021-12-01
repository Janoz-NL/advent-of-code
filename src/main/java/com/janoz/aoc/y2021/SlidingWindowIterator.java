package com.janoz.aoc.y2021;

import java.util.Arrays;
import java.util.Iterator;

public class SlidingWindowIterator implements Iterator<Integer> {
    private static final int BUFFER_SIZE = 3;
    int[] buffer = new int[BUFFER_SIZE];
    int curpos = 0;
    Iterator<Integer> input;

    SlidingWindowIterator(Iterator<Integer> input) {
        this.input = input;
        for (int i = 1; i < BUFFER_SIZE; i++) {
            nextToBuffer();
        }
    }

    private void nextToBuffer() {
        buffer[curpos] = input.next();
        curpos = (curpos + 1) % BUFFER_SIZE;
    }

    private int sumBuffer() {
        return Arrays.stream(buffer).sum();
    }

    @Override
    public boolean hasNext() {
        return input.hasNext();
    }

    @Override
    public Integer next() {
        nextToBuffer();
        return sumBuffer();
    }
}
