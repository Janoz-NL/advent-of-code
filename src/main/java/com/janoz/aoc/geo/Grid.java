package com.janoz.aoc.geo;

import com.janoz.aoc.collections.MergingMap;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public interface Grid<T> {

    void put(Point p, T v);
    T put(Point p, Function<T,T> f);
    T get(Point p);

    default T get(int x, int y) {
        return get(new Point(x,y));
    }

    default boolean inGrid(Point p) {
        return
                p.x >=0 && p.x < getWidth() &&
                p.y >=0 && p.y < getHeight();
    }

    default Predicate<Point> inBoundsPredicate() {
        return this::inGrid;
    }

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

    default Map<Integer, Set<Point>> connectedSets() {
        Iterator<Integer> nextRegionSupplier = IntStream.iterate(1, l->l+1).iterator();
        Map<Point, Integer> regions = new HashMap<>();
        MergingMap regionMapper = new MergingMap();
        Stack<Point> stack = new Stack<>();
        stack.push(Point.ORIGIN);
        regions.put(Point.ORIGIN,nextRegionSupplier.next());
        while (!stack.isEmpty()) {
            Point p = stack.pop();
            T value = get(p);
            int region = regions.get(p);
            p.streamNeighbour(this::inGrid).forEach(n-> {
                if (get(n).equals(value)) {
                    int otherRegion = regions.getOrDefault(n,0);
                    if (otherRegion == 0) {
                        regions.put(n,region);
                        stack.push(n);
                    } else if (otherRegion != region) {
                        regionMapper.addMapping(region, otherRegion);
                    }
                } else {
                    if (!regions.containsKey(n)) {
                        regions.put(n,nextRegionSupplier.next());
                        stack.push(n);
                    }
                }
            });
        }
        Map<Integer, Set<Point>> connectedSets = new HashMap<>();
        regions.forEach((k,v) -> {
           int region = regionMapper.getActual(v);
           if (!connectedSets.containsKey(region)) {
               connectedSets.put(region, new HashSet<>());
           }
           connectedSets.get(region).add(k);
        });
        return connectedSets;
    }

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
