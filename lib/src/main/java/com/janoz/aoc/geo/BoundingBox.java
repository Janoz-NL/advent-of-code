package com.janoz.aoc.geo;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BoundingBox {
    int top = Integer.MAX_VALUE;
    int bottom = Integer.MIN_VALUE;
    int left = Integer.MAX_VALUE;
    int right = Integer.MIN_VALUE;

    public BoundingBox(Point origin) {
        top = origin.y;
        bottom = origin.y;
        left= origin.x;
        right = origin.x;
    }

    public BoundingBox(Point... points) {
        for (Point point : points) {
            addPoint(point);
        }
    }

    public BoundingBox(Collection<Point> points) {
        for (Point point : points) {
            addPoint(point);
        }
    }

    public void addPoint(Point toAdd) {
        top = Math.min(top,toAdd.y);
        bottom = Math.max(bottom,toAdd.y);
        left = Math.min(left,toAdd.x);
        right = Math.max(right,toAdd.x);
    }

    public Point topLeft() {
        return new Point(left,top);
    }

    public Point bottomRight() {
        return new Point(right, bottom);
    }

    public Predicate<Point> inBoundsPredicate() {
        return Point.boundsPredicate(left,right,top, bottom);
    }

    public void printGrid(Function<Point,Character> mapToChar) {
        consumeAllPoints(p -> System.out.print(mapToChar.apply(p)), System.out::println);
    }

    public static BoundingBox readGrid(Iterator<String> input, BiConsumer<Point, Character> itemProcessor) {
        BoundingBox result = new BoundingBox(new Point(0, 0));
        Grid.readGrid(input, result::addPoint, itemProcessor);
        return result;
    }

    public Stream<Point> streamAllPoints() {
        return IntStream.rangeClosed(top, bottom)
                .mapToObj(y-> IntStream.rangeClosed(left,right)
                        .mapToObj(x -> new Point(x,y)))
                .flatMap(p -> p);
    }

    public void consumeAllPoints(Consumer<Point> pointConsumer, Runnable newLineAction) {
        IntStream.rangeClosed(top,bottom).forEach(y -> {
            IntStream.rangeClosed(left,right).mapToObj(x -> new Point(x,y)).forEach(pointConsumer);
            newLineAction.run();
        });
    }

    public int getWidth() {
        return right - left + 1;
    }

    public int getHeight() {
        return bottom - top + 1;
    }
}
