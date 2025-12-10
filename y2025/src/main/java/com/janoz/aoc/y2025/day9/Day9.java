package com.janoz.aoc.y2025.day9;

import com.janoz.aoc.StopWatch;
import com.janoz.aoc.collections.LongTuple;
import com.janoz.aoc.geo.Point;
import com.janoz.aoc.input.AocInput;

import java.util.List;
import java.util.stream.IntStream;

public class Day9 {

    public static void main(String[] args) {
        StopWatch.start();
        /*Warm up JIT*/ solve(AocInput.of(2025,9));
        StopWatch.stopPrint();

        StopWatch.start();
        LongTuple result = solve(AocInput.of(2025,9));
        StopWatch.stopPrint();
        printResult(result);
    }

    static List<Point> points;
    static List<StraightLine> edges;

    static LongTuple solve(AocInput<String> input) {
        points = input.addMapper(Point::parse).asList();
        initEdges();
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
        return edges.stream().noneMatch(s::contains);
    }

    static void initEdges() {
        edges = IntStream
                .range(0, points.size())
                .mapToObj(i -> new StraightLine(points.get(i), points.get((i + 1) % points.size())))
                .toList();
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
        final int min;
        final int max;
        final boolean horizontal;

        StraightLine(Point p1, Point p2) {
            this.p = p1;
            horizontal = p1.y == p2.y;
            if (horizontal) {
                min = Math.min(p1.x, p2.x);
                max = Math.max(p1.x, p2.x);
            } else {
                min = Math.min(p1.y, p2.y);
                max = Math.max(p1.y, p2.y);
            }
        }

        /**
         * @param s a square
         * @return true if part of this line is inside the square
         */
        boolean hasPointsInside(Square s) {
            if (horizontal)
                return p.y > s.miny && p.y < s.maxy && min < s.maxx && max > s.minx;
            else
                return p.x > s.minx && p.x < s.maxx && min < s.maxy && max > s.miny;
        }
    }
}
