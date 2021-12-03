package com.janoz.aoc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.function.Function;

public class InputIterable<T> implements Iterable<T> {

    private InternalIterator iterator;

    public InputIterable(String file, Function<String,T> mapper) throws IOException {
        this.iterator = new InternalIterator(file, mapper);
    }

    @Override
    public Iterator<T> iterator() {
        return iterator;
    }

    private class InternalIterator implements Iterator<T> {
        private String next;
        private BufferedReader input;
        private Function<String,T> mapper;

        public InternalIterator(String file, Function<String,T> mapper) throws IOException {
            input = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(file)));
            next = input.readLine();
            this.mapper = mapper;
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public T next() {
            try {
                T answer = mapper.apply(next);
                next = input.readLine();
                return answer;
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(),e);
            }
        }


    }
}
