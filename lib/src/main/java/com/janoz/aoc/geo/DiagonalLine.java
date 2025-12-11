package com.janoz.aoc.geo;

import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DiagonalLine implements Line {
    public Point start;
    public Point end;

    protected DiagonalLine(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Still has horizontal and vertical lines implemented, although they should be handled by StraightLine
     *
     * @return a stream of points that make up the line
     */
    @Override
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

    @Override
    public boolean isStraight() {
        return false;
    }

    /**
     * Same line can be unequal if it goes the other way
     * p1 -> p2 != p2 -> p1
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiagonalLine line = (DiagonalLine) o;
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
