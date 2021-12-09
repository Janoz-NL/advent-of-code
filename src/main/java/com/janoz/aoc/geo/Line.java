package com.janoz.aoc.geo;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Line {
    Point start;
    Point end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
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

    @Override
    public String toString() {
        return start.toString() + " -> " + end.toString();
    }
}
