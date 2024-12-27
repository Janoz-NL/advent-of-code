package com.janoz.aoc.geo;

import com.janoz.aoc.collections.IntRange;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * ONLY straight or stricktly diagonal lines
 *
 */
public class Line {
    Point start;
    Point end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public <T> void drawOn(GrowingGrid<T> g, T v) {
        draw().forEach(p->p.putOn(g,v));
    }

    public <T> void drawOn(GrowingGrid<T> g, Function<T,T> f) {
        draw().forEach(p->p.putOn(g,f));
    }

    public Stream<Point> draw() {
        if (start.x == end.x) {
            return IntStream.rangeClosed(Math.min(start.y, end.y), Math.max(start.y, end.y)).mapToObj(y -> new Point(start.x, y));
        } else {
            int y, deltaY;
            int x, deltaX;
            if (start.x < end.x) {
                y = start.y;
                deltaY = (int)Math.signum(end.y-start.y);
                x = start.x;
                deltaX = end.x - start.x;
            } else {
                y=end.y;
                deltaY = (int)Math.signum(start.y-end.y);
                x = end.x;
                deltaX = start.x - end.x;
            }
            return IntStream.rangeClosed(0, deltaX).mapToObj(d -> new Point(x + d, y + d * deltaY));
        }
    }

    public boolean isStraight() {
        return start.x == end.x || start.y == end.y;
    }

    public static Line parse(String s){
        String[] parts = s.split("\\s*->\\s*");
        return new Line(Point.parse(parts[0]), Point.parse(parts[1]));
    }


    /**
     * Same line can be unequal if it goes the other way
     * p1 -> p2 != p2 -> p1
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Objects.equals(start, line.start) && Objects.equals(end, line.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return start.toString() + " -> " + end.toString();
    }
}
