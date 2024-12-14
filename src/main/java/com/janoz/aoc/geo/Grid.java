package com.janoz.aoc.geo;

import com.janoz.aoc.collections.MergingMap;
import com.janoz.aoc.collections.SetAsMap;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
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

    /**
     * @return a Stream of all points in scan order
     */
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

    default BufferedImage toImage(Function<T,Color> mapper) {
        return toImage(mapper, BufferedImage.TYPE_INT_ARGB);
    }

    default BufferedImage toImage(Function<T,Color> mapper, int type) {
        ToIntFunction<Color> toInt = Color::getRGB;
        BufferedImage image = new BufferedImage(getWidth(),getHeight(), type);
        Iterator<Point> it = streamPoints().iterator();
        if (!it.hasNext()) {
            return null;
        }
        while (it.hasNext()) {
            Point p = it.next();
            image.setRGB(p.x, p.y, toInt.applyAsInt(mapper.apply(get(p))));
        }
        return image;
    }

    default BufferedImage toBigImage(Function<T,Color> mapper,int blocksize, int bordersize) {
        ToIntFunction<Color> toInt = Color::getRGB;
        BufferedImage image = new BufferedImage(getWidth() * blocksize + bordersize,getHeight() * blocksize + bordersize, BufferedImage.TYPE_INT_ARGB);
        Iterator<Point> it = streamPoints().iterator();
        if (!it.hasNext()) {
            return null;
        }
        while (it.hasNext()) {
            Point p = it.next();
            for (int x=bordersize;x<blocksize;x++){
                for (int y=bordersize;y<blocksize;y++){
                    image.setRGB(p.x * blocksize + x, p.y * blocksize + y, toInt.applyAsInt(mapper.apply(get(p))));
                }
            }
        }
        return image;
    }

    default Map<Integer, Set<Point>> connectedSets() {
        Iterator<Integer> nextRegionSupplier = IntStream.iterate(1, l->l+1).iterator();
        Map<Point, Integer> regions = new HashMap<>();
        MergingMap regionMapper = new MergingMap();
        regions.put(Point.ORIGIN,nextRegionSupplier.next());
        streamPoints().forEach(p -> {
            T value = get(p);
            int region = regions.get(p);
            Arrays.stream(new Point[]{p.south(),p.east()}).filter(this::inGrid).forEach(n-> {
                int actualRegion = regionMapper.getActual(region);
                if (get(n).equals(value)) {
                    //same connected set
                    int neighbourRegion = regionMapper.getActual(regions.getOrDefault(n,0));
                    if (neighbourRegion == 0) {
                        //new point
                        regions.put(n,actualRegion);
                    } else if (neighbourRegion != actualRegion) {
                        //existing point, merge sets
                        regionMapper.addMapping(actualRegion, neighbourRegion);
                    }
                } else {
                    if (!regions.containsKey(n)) {
                        //new point, new set
                        int nextRegion = nextRegionSupplier.next();
                        regions.put(n, nextRegion);
                    }
                }
            });
        });
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

    /**
     * randomly walk through the grid creating sets for some nice animations
     * @param frameConsumer
     * @return
     */
    default Map<Integer, Set<Point>> connectedSets(Consumer<Grid<Integer>> frameConsumer) {
        Iterator<Integer> nextRegionSupplier = IntStream.iterate(1, l->l+1).iterator();
        Map<Point, Integer> regions = new HashMap<>();
        MergingMap regionMapper = new MergingMap();
        PriorityQueue<Point> queue = new PriorityQueue<>(Comparator.comparingInt(Point::randHash));
        Point middle = new Point(getWidth() >> 1, getHeight() >> 1);
        queue.add(middle);
        regions.put(middle,nextRegionSupplier.next());
        while (!queue.isEmpty()) {
            Point p = queue.poll();
            T value = get(p);
            int region = regions.get(p);
            p.streamNeighbour(this::inGrid).forEach(n-> {
                int actualRegion = regionMapper.getActual(region);
                if (get(n).equals(value)) {
                    //same connected set
                    int neighbourRegion = regionMapper.getActual(regions.getOrDefault(n,0));
                    if (neighbourRegion == 0) {
                        //new point
                        regions.put(n,actualRegion);
                        frameConsumer.accept(asGrid(getWidth(),getHeight(), regions, regionMapper::getActual, x->x == actualRegion));
                        queue.add(n);
                    } else if (neighbourRegion != actualRegion) {
                        //existing point, merge sets
                        int resultRegion = regionMapper.addMapping(actualRegion, neighbourRegion);
                        frameConsumer.accept(asGrid(getWidth(),getHeight(), regions, regionMapper::getActual, x->x == resultRegion));
                    }
                } else {
                    if (!regions.containsKey(n)) {
                        //new point, new set
                        int nextRegion = nextRegionSupplier.next();
                        regions.put(n, nextRegion);
                        frameConsumer.accept(asGrid(getWidth(),getHeight(), regions, x->x, x->x == nextRegion));
                        queue.add(n);
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

    static Grid<Boolean> asGrid(int width, int height, Set<Point> points) {
        return asGrid(width, height, new SetAsMap<>(points), x->x);
    }

    static <T> Grid<T> asGrid(int width, int height, Map<Point, T> data, Function<T,T> transformer) {
        return asGrid(width, height, data, transformer, x->true);
    }

    static <T> Grid<T> asGrid(int width, int height, Map<Point, T> data, Function<T,T> transformer, Predicate<T> filter) {
        return new Grid<>() {

            @Override
            public void put(Point p, T v) {
                throw new UnsupportedOperationException();
            }

            @Override
            public T put(Point p, Function<T, T> f) {
                throw new UnsupportedOperationException();
            }

            @Override
            public T get(Point p) {
                return transformer.apply(data.get(p));
            }

            @Override
            public int getWidth() {
                return width;
            }

            @Override
            public int getHeight() {
                return height;
            }

            @Override
            public Stream<Point> streamPoints() {
                return data.keySet().stream().filter(p -> filter.test(get(p)));
            }
        };
    }
}
