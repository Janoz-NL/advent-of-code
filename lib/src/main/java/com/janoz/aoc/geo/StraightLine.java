package com.janoz.aoc.geo;

import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 */
public class StraightLine implements Line{

    public final Point start;
    public final Point end;
    public final int min;
    public final int max;
    public final boolean horizontal;



    protected StraightLine(Point start, Point end) {
        this.start = start;
        this.end = end;
        horizontal = start.y == end.y;
        if (horizontal) {
            min = Math.min(start.x, end.x);
            max = Math.max(start.x, end.x);
        } else {
            min = Math.min(start.y, end.y);
            max = Math.max(start.y, end.y);
        }
    }

    @Override
    public boolean isStraight() {
        return true;
    }

    @Override
    public Stream<Point> draw() {
        return IntStream.rangeClosed(min,max).mapToObj(i -> horizontal? new Point(i,start.y) : new Point(start.x,i));
    }

    /**
     * @param bb the square to check
     * @return true if part of this line is inside the square (edges are ignored)
     */
    public boolean hasPointsInside(BoundingBox bb) {
        if (horizontal)
            return start.y > bb.top && start.y < bb.bottom && min < bb.right && max > bb.left;
        else
            return start.x > bb.left && start.x < bb.right && min < bb.bottom && max > bb.top;
    }

    /**
     * Only vertical
     */
    public boolean emptyRight() {
        return min == start.y;
    }

    /**
     * only horizontal
     */
    public boolean emptyTop() {
        return min == start.x;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StraightLine line = (StraightLine) o;
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
