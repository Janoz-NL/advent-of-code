package com.janoz.aoc.geo;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Histogram backed implementation of a grid
 * @param <T>
 */
public class HistoGrid<T> implements Grid<T> {
    private final Map<T, Set<Point>> data;
    private final int width;
    private final int height;

    public HistoGrid(int width, int height) {
        this(width,height,new HashMap<>());
    }

    /**
     *
     * @param width
     * @param height
     * @param data initial data. A point should only be available on 1 of the sets
     */
    public HistoGrid(int width, int height, Map<T, Set<Point>> data) {
        this.data = data;
        this.width = width;
        this.height = height;
    }

    @Override
    public void put(Point p, T v) {
        for (Set<Point> points : data.values()) {
            points.remove(p);
        }
        if (!data.containsKey(v)) {
            data.put(v, new HashSet<>());
        }
        data.get(v).add(p);
    }

    public void put(Collection<Point> ps, T v) {
        for (Set<Point> points : data.values()) {
            points.removeAll(ps);
        }
        if (!data.containsKey(v)) {
            data.put(v, new HashSet<>());
        }
        data.get(v).addAll(ps);
    }

    @Override
    public T put(Point p, Function<T, T> f) {
        //not Implemented
        throw new UnsupportedOperationException();
    }

    @Override
    public T get(Point p) {
        for (Map.Entry<T, Set<Point>> e : data.entrySet()) {
            if (e.getValue().contains(p)) return e.getKey();
        }
        return null;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public Stream<Point> streamPoints() {
        return data.values().stream().flatMap(Set::stream);
    }
}
