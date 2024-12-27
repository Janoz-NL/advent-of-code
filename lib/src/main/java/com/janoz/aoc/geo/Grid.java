package com.janoz.aoc.geo;

import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

public interface Grid<T> extends ReadOnlyGrid<T> {

    void put(Point p, T v);
    T put(Point p, Function<T,T> f);

    static void readGrid(Iterator<String> input, Consumer<Point> touch, BiConsumer<Point, Character> store) {
        readGrid(input, touch, store, c->c, '.');
    }

    static <T> void readGrid(Iterator<String> input, Consumer<Point> touch, BiConsumer<Point, T> store, Function<Character,T> mapper, T emptyValue) {
        Iterator<Integer> ySupplier = IntStream.iterate(0, i -> i+1).iterator();
        String line = input.next();
        touch.accept(new Point(line.length()-1,0));
        int y;
        do {
            y = ySupplier.next();
            for (int x=0;x<line.length(); x++) {
                T val = mapper.apply(line.charAt(x));
                if (val != emptyValue) store.accept(new Point(x,y), val);
            }
            if (!input.hasNext()) break;
            line = input.next();
            if (line.isEmpty()) break;
        } while (true);
        touch.accept(new Point(0,y));
    }
}
