package com.janoz.aoc.y2025.day9;

import com.janoz.aoc.StopWatch;
import com.janoz.aoc.collections.LongTuple;
import com.janoz.aoc.geo.Line;
import com.janoz.aoc.geo.Point;
import com.janoz.aoc.geo.Rectangle;
import com.janoz.aoc.geo.StraightLine;
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
        Rectangle rectangle;
        for (int i = 0; i < points.size() - 1; i++) {
            for (int j = i + 1; j < points.size(); j++) {
                rectangle = new Rectangle(points.get(i), points.get(j));
                long area = rectangle.area();
                max1 = Math.max(area,max1);
                if (rectangle.area() > max2 && fits(rectangle)) {
                    max2 = area;
                }
            }
        }
        return new LongTuple(max1, max2);
    }

    static boolean fits(Rectangle rectangle) {
        if (edges.stream().noneMatch(rectangle::contains)) {
            return isInside(rectangle.centerish());
        }
        return false;
    }

    static boolean isInside(Point p) {
        StraightLine left = null;
        StraightLine right = null;
        StraightLine top = null;
        StraightLine bottom = null;

        for (StraightLine edge : edges) {
            if (edge.horizontal) {
                if (p.x > edge.min && p.x < edge.max) {
                    if (edge.start.y < p.y) {
                        //above
                        if (top == null || top.start.y < edge.start.y) top = edge;
                    } else {
                        //below
                        if (bottom == null || bottom.start.y > edge.start.y) bottom = edge;
                    }
                }
            } else {
                if (p.y > edge.min && p.y < edge.max) {
                    if (edge.start.x < p.x) {
                        //left
                        if (left == null || left.start.x < edge.start.x) left = edge;
                    } else {
                        //right
                        if (right == null || right.start.x > edge.start.x) right = edge;
                    }
                }
            }
        }
        //closes edges determined
        if (top != null && bottom != null && left != null && right != null) {
            if (!top.emptyTop())
                return false;
            if (bottom.emptyTop())
                return false;
            if (left.emptyRight())
                return false;
            if (!right.emptyRight())
                return false;
            return true;
        }
        return false;
    }

    static void initEdges() {
        edges = IntStream
                .range(0, points.size())
                .mapToObj(i -> Line.ofStraight(points.get(i), points.get((i + 1) % points.size())))
                .toList();
    }

    static void printResult(LongTuple results) {
        System.out.printf("Part 1 : %d\nPart 2 : %d",results.getLeft(), results.getRight());
    }
}
