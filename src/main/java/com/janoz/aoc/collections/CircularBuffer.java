package com.janoz.aoc.collections;

import java.io.IOException;
import java.io.InputStream;

public class CircularBuffer {

    private int pos=0;
    private int[] buffer;
    private InputStream input;

    public CircularBuffer(InputStream input, int size) {
        buffer = new int[size];
        this.input = input;
    }

    public int readNext() throws IOException {
        pos = (pos + 1) % buffer.length;
        buffer[pos] = input.read();
        return buffer[pos];
    }

    public int get(int offset) {
        return buffer[(pos + buffer.length - offset) % buffer.length];
    }
}
