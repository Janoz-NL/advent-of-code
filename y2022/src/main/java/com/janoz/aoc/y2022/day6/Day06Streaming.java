package com.janoz.aoc.y2022.day6;

import com.janoz.aoc.collections.CircularBuffer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Day06Streaming {

    public Day06Streaming(InputStream inputStream, int headersize) throws IOException {
        this.in = new BufferedInputStream(inputStream);
        this.headersize = headersize;
        find();
        System.out.println(signalpos);
    }

    CircularBuffer buffer;

    private final int[] chars = new int[26];
    private int dupes;
    private int signalpos = 0;
    private final int headersize;
    private final InputStream in;

    private void find() throws IOException {
        buffer = new CircularBuffer(in, headersize+1);
        dupes = 0;
        for (int i =0; i<headersize; i++) {
            add();
        }
        while (dupes > 0) {
            remove();
            add();
        }
    }

    private void add() throws IOException {
        int i = buffer.readNext() - 'a';
        chars[i]++;
        if (chars[i] == 2) dupes++;
        signalpos++;
    }

    private void remove() {
        int i = buffer.get(headersize-1) - 'a';
        chars[i]--;
        if (chars[i]==1) dupes--;
    }
}
