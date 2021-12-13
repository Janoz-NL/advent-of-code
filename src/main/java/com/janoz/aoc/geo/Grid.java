package com.janoz.aoc.geo;

import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public interface Grid<T> {

    void put(Point p, T v);
    T put(Point p, Function<T,T> f);
    T get(Point p);

    default Stream<T> streamValues() {
        return streamPoints().map(this::get);
    }

    default Stream<Point> streamPoints() {
        return IntStream.range(0,getHeight()).mapToObj( y-> IntStream.range(0,getWidth()).mapToObj(x -> new Point(x,y))).flatMap(x -> x);
    }

    default String toString(Function<T,String> formatter) {
        StringBuilder sb = new StringBuilder();
        System.out.println();
        for (int y=0;y<getHeight();y++) {
            for (int x=0;x<getWidth();x++) {
                sb.append(formatter.apply(get(new Point(x,y))));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    int getWidth();
    int getHeight();


}
