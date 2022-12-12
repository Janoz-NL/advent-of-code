package com.janoz.aoc.geo;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Automatically growing 2D field
 * @param <T> type of gridpoints
 */
public class GrowingGrid<T> implements Grid<T>{

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

}
