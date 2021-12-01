package com.janoz.aoc.y2021;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

public class InputIterator implements Iterator<Integer> {

    private String next;
    private BufferedReader input;

    public InputIterator(String file) throws IOException {
        input = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(file)));
        next = input.readLine();
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public Integer next() {
        try {
            Integer answer = Integer.parseInt(next);
            next = input.readLine();
            return answer;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }
}
