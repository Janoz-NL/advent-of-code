package com.janoz.aoc.y2022.day6;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class Day06Streaming {

    public Day06Streaming(InputStream inputStream, int headersize) throws IOException {
        this.in = new BufferedInputStream(inputStream);
        this.headersize = headersize;
        find();
        System.out.println(signalpos);
    }


    private int[] chars = new int[26];
    private int dupes;
    private int[] buffer;
    private int bufpos = 0;
    private int signalpos = 0;
    private int headersize;
    private InputStream in;

    private void find() throws IOException {
        Arrays.fill(chars,0);
        dupes = 0;
        buffer = new int[headersize];
        for (int i =0; i<headersize; i++) {
            add();
        }
        while (dupes > 0) {
            remove();
            add();
        }
    }

    private int getNext() throws IOException {
        return in.read() - 'a';
    }

    private void add() throws IOException {
        int i = getNext();
        buffer[bufpos] = i;
        chars[i]++;
        if (chars[i] == 2) dupes++;
        bufpos = (bufpos + 1) % headersize;
        signalpos++;
    }

    private void remove() {
        int i = buffer[bufpos];
        chars[i]--;
        if (chars[i]==1) dupes--;
    }
}
