package com.janoz.aoc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class InputProcessor<T> implements Iterable<T> {

    private final InternalIterator iterator;

    public static BufferedReader getReaderFromResource(String file) {

        return new BufferedReader(new InputStreamReader(Objects.requireNonNull(InputProcessor.class.getClassLoader().getResourceAsStream(file))));
    }


    public InputProcessor(String file, Function<String,T> mapper) {
        this(
                getReaderFromResource(file),
                mapper,
                false
        );
    }

    public InputProcessor(BufferedReader reader, Function<String,T> mapper) {
        this.iterator = new InternalIterator(reader, mapper, true);
    }

    public InputProcessor(BufferedReader reader, Function<String,T> mapper, boolean stopIteratorWhenLineEmpty) {
        this.iterator = new InternalIterator(reader, mapper, stopIteratorWhenLineEmpty);
    }

    @Override
    public Iterator<T> iterator() {
        return iterator;
    }

    public Stream<T> stream() {
        return StreamSupport.stream(spliterator(),false);
    }

    public List<T> asList() {
        return stream().collect(Collectors.toList());
    }


    private class InternalIterator implements Iterator<T> {
        private String next;
        private final BufferedReader input;
        private final Function<String,T> mapper;
        private final boolean stopIteratorWhenLineEmpty;

        public InternalIterator(BufferedReader input, Function<String,T> mapper, boolean stopIteratorWhenLineEmpty) {
            this.input = input;
            this.mapper = mapper;
            this.stopIteratorWhenLineEmpty = stopIteratorWhenLineEmpty;
            getNext();
        }

        @Override
        public boolean hasNext() {
            return next != null &&
                    (!stopIteratorWhenLineEmpty || next.length()>0);
        }

        @Override
        public T next() {
                T answer = mapper.apply(next);
                getNext();
                return answer;
        }

        private void getNext() {
            try {
                next = input.readLine();
                if (next != null) next = next.trim();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(),e);
            }
        }
    }
}
