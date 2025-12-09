package com.janoz.aoc.y2025.day9;

import com.janoz.aoc.StopWatch;
import com.janoz.aoc.collections.LongTuple;
import com.janoz.aoc.geo.Point;
import com.janoz.aoc.input.AocInput;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day9 {

    public static void main(String[] args) {
        StopWatch.start();
        LongTuple result = solve(AocInput.of(2025,9));
        StopWatch.stopPrint();
        printResult(result);
    }

    static List<Point> points;

    static LongTuple solve(AocInput<String> input) {
        points = input.addMapper(Point::parse).asList();
        long max1 = 0;
        long max2 = 0;
        Square s;
        for (int i = 0; i < points.size() - 1; i++) {
            for (int j = i + 1; j < points.size(); j++) {
                s = new Square(points.get(i), points.get(j));
                long area = s.area();
                max1 = Math.max(area,max1);
                if (s.area() > max2 && fits(s)) {
                    max2 = area;
                }
            }
        }
        return new LongTuple(max1, max2);
    }

    static boolean fits(Square s) {
        return streamEdges().noneMatch(s::contains);
    }

    static Stream<StraightLine> streamEdges() {
        return IntStream.range(0,points.size()).boxed().map(i -> new StraightLine(points.get(i), points.get((i+1)%points.size())));
    }

    static void printResult(LongTuple results) {
        System.out.printf("Part 1 : %d\nPart 2 : %d",results.getLeft(), results.getRight());
    }

    static class Square {
        final int minx;
        final int maxx;
        final int miny;
        final int maxy;

        public Square(Point p1, Point p2) {
            minx = Math.min(p1.x, p2.x);
            maxx = Math.max(p1.x, p2.x);
            miny = Math.min(p1.y, p2.y);
            maxy = Math.max(p1.y, p2.y);
        }

        long area() {
            return (maxx-minx+1L) * (maxy-miny+1L);
        }

        boolean contains(StraightLine l) {
            return l.hasPointsInside(this);
        }
    }

    static class StraightLine {
        final Point p;
        final int minx;
        final int maxx;
        final int miny;
        final int maxy;
        final boolean horizontal;

        StraightLine(Point p1, Point p2) {
            this.p = p1;
            minx = Math.min(p1.x, p2.x);
            maxx = Math.max(p1.x, p2.x);
            miny = Math.min(p1.y, p2.y);
            maxy = Math.max(p1.y, p2.y);
            horizontal = p1.y == p2.y;
        }

        /**
         * @param s a square
         * @return true if part of this line is inside the square
         */
        boolean hasPointsInside(Square s) {
            if (horizontal)
                return p.y > s.miny && p.y < s.maxy && minx < s.maxx && maxx > s.minx;
            else
                return p.x > s.minx && p.x < s.maxx && miny < s.maxy && maxy > s.miny;
        }
    }
}
