package com.janoz.aoc.geo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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

    public int minX = Integer.MAX_VALUE;
    public int minY = Integer.MAX_VALUE;

    int width=0;
    int height=0;


    Map<Point,T> data = new HashMap<>();

    final private T emptyValue;

    public GrowingGrid(T emptyValue) {
        this.emptyValue = emptyValue;
    }

    public GrowingGrid<T> copy() {
        GrowingGrid<T> result = new GrowingGrid<>(emptyValue);
        result.data = new HashMap<>(data);
        result.width = width;
        result.height = height;
        result.minY = minY;
        result.minX = minX;
        return result;
    }

    public void clear() {
        data.clear();
    }

    public void put(Point p, T value) {
        grow(p);
        cheapPut(p,value);
    }

    public T put(Point p, Function<T,T> func) {
        grow(p);
        T value = func.apply (get(p));
        cheapPut(p, value);
        return value;
    }

    private void cheapPut(Point p, T value) {
        if (Objects.equals(value,emptyValue)) data.remove(p);
        else data.put(p,value);
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

    /**
     * Check without growing grid
     */
    public T peek(Point p) {
        return data.getOrDefault(p,emptyValue);
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

    /**
     * TODO: Actualy maxX+1.
     */
    public int getWidth() {
        return width;
    }

    /**
     * TODO: Actualy maxY+1.
     */
    public int getHeight() {
        return height;
    }

    public void printGrid(Function<T,Character> mapToChar) {
        IntStream.range(minY,height).forEach(y -> {
            System.out.print("" + y + '\t');
            streamRow(y).map(mapToChar).forEach(System.out::print);
            System.out.println();
        });
    }

    public Stream<T> streamRow(int y) {
        return IntStream.range(minX,width).mapToObj(x -> new Point(x,y)).map(this::peek);
    }

    public Stream<T> streamCol(int x) {
        return IntStream.range(minY,height).mapToObj(y -> new Point(x,y)).map(this::peek);
    }

    public void shrink() {
        while (streamRow(minY).allMatch(i -> Objects.equals(i,emptyValue))){
            minY++;
        }
        while (streamCol(width-1).allMatch(i -> Objects.equals(i,emptyValue))){
            width--;
        }
        while (streamRow(height-1).allMatch(i -> Objects.equals(i,emptyValue))){
            height--;
        }
        while (streamCol(minX).allMatch(i -> Objects.equals(i,emptyValue))){
            minX++;
        }
    }
}
