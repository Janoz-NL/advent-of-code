package com.janoz.aoc.geo;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Automatically growing 2D field
 * @param <T> type of gridpoints
 *
 *           TODO : Implement negative points
 *           TODO : refactor width, height, minX, minY to top, left, bottom, right
 */
public class GrowingGrid<T> implements Grid<T>{

    int minX = Integer.MAX_VALUE;
    int minY = Integer.MAX_VALUE;

    int width=0;
    int height=0;
    Map<Point,T> data = new HashMap<>();

    final private T emptyValue;

    public GrowingGrid(T emptyValue) {
        this.emptyValue = emptyValue;
    }

    public void clear() {
        data.clear();
    }

    public void put(Point p, T value) {
        grow(p);
        data.put(p,value);
    }

    public T put(Point p, Function<T,T> func) {
        grow(p);
        T value = func.apply (get(p));
        data.put(p, value);
        return value;
    }

    public void setHeight(int height) {
        if (this.height > height)
            data.entrySet().removeIf(p -> p.getKey().y >= height);
        this.height = height;
    }

    public void setWidth(int width) {
        if (this.width > width)
            data.entrySet().removeIf(p -> p.getKey().x >= width);
        this.width = width;
    }

    public T get(Point p) {
        grow(p);
        return data.getOrDefault(p,emptyValue);
    }

    public boolean everPut(Point p) {
        return data.containsKey(p);
    }

    private void grow(Point p) {
        width=Math.max(width,p.x+1);
        height=Math.max(height,p.y+1);
        minX=Math.min(minX,p.x);
        minY=Math.min(minY,p.y);
    }

    @Override
    public Stream<T> streamValues() {
        return data.values().stream();
    }

    public long amountOfPutPoints() {
        return data.size();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void printGrid(Function<T,Character> mapToChar) {
        IntStream.range(minY,height).forEach(y -> {
            IntStream.range(minX,width).mapToObj(x -> new Point(x,y)).map(this::get).map(mapToChar).forEach(System.out::print);
            System.out.println();
        });
    }
}
