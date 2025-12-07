package com.janoz.aoc.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class AocInput<T> {

    private final Function<String,T> mapper;
    private final BufferedReader reader;


    private AocInput(Reader reader, Function<String,T> mapper) {
        this.reader = new BufferedReader(reader);
        this.mapper = mapper;
    }

    private <I> AocInput(AocInput<I> builder, Function<I,T> mapper) {
        this.reader = builder.reader;
        this.mapper = builder.mapper.andThen(mapper);
    }

    public <R> AocInput<R> addMapper(Function<T,R> mapper) {
        return new AocInput<>(this,mapper);
    }

    private InternalIterator iterator;

    private Iterator<T> getIterator() {
        if (iterator == null ) {
            iterator = new InternalIterator(reader, mapper);
            return iterator;
        } else if (iterator.hasNextPart()) {
            iterator.next();
            return iterator;
        }
        return null;
    }

    public boolean hasNextPart() {
        return iterator == null || iterator.hasNextPart();
    }

    public Iterator<T> iterator() {
        return getIterator();
    }

    public Iterable<T> iterable() {
        return stream()::iterator;
    }

    public Stream<T> stream() {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator(),0),false);
    }

    public List<T> asList() {
        return stream().collect(Collectors.toList());
    }

    public String asString(Function<T,String> mapper) {
        return stream().map(mapper).collect(Collectors.joining("\n"));
    }

    public static AocInput<String> ofFile(File input) throws FileNotFoundException {
        return ofFile(input, String::trim);
    }

    public static AocInput<String> ofString(String input) {
        return ofString(input, String::trim);
    }

    public static AocInput<String> of(int year, int day) {
        return of(year,day, String::trim);
    }

    public static AocInput<String> ofFile(File input, Function<String,String> mapper) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(input);
        return new AocInput<>(new InputStreamReader(inputStream, StandardCharsets.UTF_8), mapper);
    }

    public static AocInput<String> ofString(String input, Function<String,String> mapper) {
        return new AocInput<>(new StringReader(input),mapper);
    }

    public static AocInput<String> of(int year, int day, Function<String,String> mapper) {
        try {
            Properties p = new Properties();
            p.load(new FileReader("./application.properties"));
            File directory = new File(p.getProperty("inputs"));
            File input = new File(directory, String.format("input_%s_%s.txt", year, day));
            if (!input.exists()) {
                directory.mkdirs();
                try (FileOutputStream fos = new FileOutputStream(input) ) {
                    getDownloadStream(year, day, p.getProperty("session")).transferTo(fos);
                } catch (IOException ioe) {
                    if (!input.delete()) {
                        input.deleteOnExit();
                    }
                    throw ioe;
                }
            }
            return ofFile(input, mapper);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe.getMessage(),ioe);
        }
    }



    private static InputStream getDownloadStream(int year, int day, String session) throws IOException {
        URL url = new URL(String.format("https://adventofcode.com/%s/day/%s/input", year, day));
        URLConnection urlConnection = url.openConnection();
        urlConnection.addRequestProperty("Cookie","session="+ session);
        return urlConnection.getInputStream();
    }

    private class InternalIterator implements Iterator<T> {
        private String next;
        private final BufferedReader input;
        private final Function<String,T> mapper;

        public InternalIterator(BufferedReader input, Function<String,T> mapper) {
            this.input = input;
            this.mapper = mapper;
            getNext();
        }

        @Override
        public boolean hasNext() {
            return next != null && !next.isBlank();
        }

        public boolean hasNextPart() {
            return !hasNext() && next != null;
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
               // if (next != null) next = next.trim();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(),e);
            }
        }
    }
}
