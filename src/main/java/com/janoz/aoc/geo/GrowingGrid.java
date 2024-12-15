package com.janoz.aoc.geo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Automatically growing 2D field
 * @param <T> type of gridpoints
 *
 */
public class GrowingGrid<T> implements Grid<T>{

    private int minY = Integer.MAX_VALUE;
    private int maxY = Integer.MIN_VALUE;
    private int minX = Integer.MAX_VALUE;
    private int maxX = Integer.MIN_VALUE;

    private Map<Point,T> data = new HashMap<>();

    final private T emptyValue;

    public GrowingGrid(T emptyValue) {
        this.emptyValue = emptyValue;
    }

    @Override
    public Point getOrigin() {
        return new Point(minX, minY);
    }

    public GrowingGrid<T> copy() {
        GrowingGrid<T> result = new GrowingGrid<>(emptyValue);
        result.data = new HashMap<>(data);
        result.minY = minY;
        result.maxY = maxY;
        result.minX = minX;
        result.maxX = maxX;
        return result;
    }

    public void clear() {
        data.clear();
    }

    /**
     * force 0,0 to be on the grid
     */
    public void forceOrigin() {
        grow(new Point(0,0));
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

    public void setMaxY(int maxY) {
        if (this.maxY > maxY)
            data.entrySet().removeIf(p -> p.getKey().y > maxY);
        this.maxY = maxY;
    }

    public void setMaxX(int maxX) {
        if (this.maxX > maxX)
            data.entrySet().removeIf(p -> p.getKey().x > maxX);
        this.maxX = maxX;
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
        maxX =Math.max(maxX,p.x);
        minY =Math.min(minY,p.y);
        minX =Math.min(minX,p.x);
        maxY =Math.max(maxY,p.y);
    }

    @Override
    public Stream<T> streamValues() {
        return data.values().stream();
    }

    public long amountOfPutPoints() {
        return data.size();
    }

    public int getWidth() {
        if (data.isEmpty()) return 0;
        return maxX - minX +1;
    }

    public int getHeight() {
        if (data.isEmpty()) return 0;
        return maxY - minY +1;
    }

    public void printGrid(Function<T,Character> mapToChar) {
        IntStream.rangeClosed(minY,maxY).forEach(y -> {
            System.out.print("" + y + '\t');
            streamRow(y).map(mapToChar).forEach(System.out::print);
            System.out.println();
        });
    }

    public Stream<T> streamRow(int y) {
        return IntStream.rangeClosed(minX,maxX).mapToObj(x -> new Point(x,y)).map(this::peek);
    }

    public Stream<T> streamCol(int x) {
        return IntStream.rangeClosed(minY,maxY).mapToObj(y -> new Point(x,y)).map(this::peek);
    }

    public Stream<Point> streamPoints(Predicate<T> filter) {
        return data.entrySet().stream().filter(e -> filter.test(e.getValue())).map(Map.Entry::getKey);
    }

    public void shrink() {
        while (streamRow(minY).allMatch(i -> Objects.equals(i,emptyValue))){
            minY++;
        }
        while (streamRow(maxY).allMatch(i -> Objects.equals(i,emptyValue))){
            maxY--;
        }
        while (streamCol(minX).allMatch(i -> Objects.equals(i,emptyValue))){
            minX++;
        }
        while (streamCol(maxX).allMatch(i -> Objects.equals(i,emptyValue))){
            maxX--;
        }
    }

    public static <T> GrowingGrid<T> readGrid(Iterator<String> input, Function<Character,T> mapper, T emptyValue) {
        GrowingGrid<T> result = new GrowingGrid<>(emptyValue);
        Grid.readGrid(input, result::grow, result::put, mapper, emptyValue);
        return result;
    }

    public static GrowingGrid<Character> readGrid(Iterator<String> input, BiConsumer<Point,Character> dataConsumer) {
        GrowingGrid<Character> result = new GrowingGrid<>('.');
        Grid.readGrid(input, result::grow, biConsumers(result::put,dataConsumer), c -> c, '.');
        return result;
    }

    public static GrowingGrid<Character> readGrid(Iterator<String> input) {
        return readGrid(input, c -> c, '.');
    }

    @SafeVarargs
    static BiConsumer<Point,Character> biConsumers(BiConsumer<Point,Character>... biConsumers) {
        return (p,c) -> {
            for (BiConsumer<Point,Character> biConsumer : biConsumers) {
                biConsumer.accept(p,c);
            }
        };
    }
}
