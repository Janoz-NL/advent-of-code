package com.janoz.aoc.geo;

import java.util.function.Function;
import java.util.stream.Stream;

public interface Grid<T> {

    void put(Point p, T v);
    T put(Point p, Function<T,T> f);
    T get(Point p);
    Stream<T> streamValues();
    int getWidth();
    int getHeight();


}
