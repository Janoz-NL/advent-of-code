package com.janoz.aoc.geo;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * ONLY straight or strictly diagonal lines (for drawing)
 *
 */
public interface Line {

     boolean isStraight();

    Stream<Point> draw();

    default <T> void drawOn(GrowingGrid<T> g, T v) {
        draw().forEach(p->p.putOn(g,v));
    }

    default <T> void drawOn(GrowingGrid<T> g, Function<T,T> f) {
        draw().forEach(p->p.putOn(g,f));
    }

    static Line parse(String s){
        String[] parts = s.split("\\s*->\\s*");
        return of(Point.parse(parts[0]), Point.parse(parts[1]));
    }

    static Line of(Point start, Point end) {
//        if (start.x == end.x || start.y == end.y) {
//            return new StraightLine(start,end);
//        }
        return new DiagonalLine(start,end);
    }

    static StraightLine ofStraight(Point start, Point end) {
        if (start.x == end.x || start.y == end.y) {
            return new StraightLine(start, end);
        } else {
            throw new IllegalArgumentException("Not a straight line");
        }
    }
}
