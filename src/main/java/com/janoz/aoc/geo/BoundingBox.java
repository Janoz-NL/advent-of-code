package com.janoz.aoc.geo;

import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class BoundingBox {
    int top,bottom,left,right;

    public BoundingBox(Point origin) {
        top = origin.y;
        bottom = origin.y;
        left= origin.x;
        right = origin.x;
    }

    public void addPoint(Point toAdd) {
        top = Math.min(top,toAdd.y);
        bottom = Math.max(bottom,toAdd.y);
        left = Math.min(left,toAdd.x);
        right = Math.max(right,toAdd.x);
    }

    public Predicate<Point> inBoundsPredicate() {
        return Point.boundsPredicate(left,right,top, bottom);
    }

    public void printGrid(Function<Point,Character> mapToChar) {
        IntStream.rangeClosed(top,bottom).forEach(y -> {
            IntStream.rangeClosed(left,right).mapToObj(x -> new Point(x,y)).map(mapToChar).forEach(System.out::print);
            System.out.println();
        });
    }

    public static BoundingBox readGrid(Iterator<String> input, BiConsumer<Point, Character> itemProcessor) {
        int y=0;
        String line = input.next();
        BoundingBox result = new BoundingBox(new Point(line.length()-1,0));
        do {
            for (int x=0;x<line.length(); x++) {
                if (line.charAt(x) != '.') itemProcessor.accept(new Point(x,y), line.charAt(x));
            }
            if (!input.hasNext()) break;
            y++;
            line = input.next();
        } while (true);
        result.addPoint(new Point(0,y));
        return result;
    }
}
