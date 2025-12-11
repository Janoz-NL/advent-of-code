package com.janoz.aoc.y2022.day14;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.StopWatch;
import com.janoz.aoc.geo.Line;
import com.janoz.aoc.geo.Point;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day14b {

    static final Point HOLE = new Point(500,0);

    static Set<Point> fall = new HashSet<>();
    static int depth = 0;

    public static void main(String[] args) {
        String file = "inputs/2022/day14.txt";
        StopWatch.start();
        loadField(file);
        int sand = 0;
        while (dropSand()) {
            sand++;
        }
        System.out.println("Part 1:" + sand);

        int bottom = depth + 2;
        while (dropSand(bottom)) {
            sand++;
        }
        System.out.println("Part 2:" + sand);
        StopWatch.stopPrint();
    }

    static boolean dropSand() {
        Point current = HOLE;
        Point next;
        while (current.y < depth) {
            next = current.south();
            if (fall.contains(next)) {
                next = current.translate(-1, 1);
                if (fall.contains(next)) {
                    next = current.translate(1, 1);
                    if (fall.contains(next)) {
                        fall.add(current);
                        return true;
                    }
                }
            }
            current = next;
        }
        return false;
    }

    static boolean dropSand(int bottom) {
        if (fall.contains(HOLE)) return false;
        Point current = HOLE;
        Point next;
        while (true) {
            if (current.y > depth) {
                fall.add(current);
                return true;
            }
            next = current.south();
            if (fall.contains(next)) {
                next = current.translate(-1, 1);
                if (fall.contains(next)) {
                    next = current.translate(1, 1);
                    if (fall.contains(next)) {
                        fall.add(current);
                        return true;
                    }
                }
            }
            current = next;
        }
    }

    static void loadField(String file) {
        InputProcessor.asStream(file, s -> Arrays.stream(s.split("->"))
                .map(String::trim)
                .map(point -> {
                    Point result = Point.parse(point);
                    depth = Math.max(depth, result.y);
                    return result;
                })
                .collect(Collectors.toList())).forEach(points ->
                        IntStream.range(1,points.size()).forEach(i ->
                                Line.of(points.get(i-1), points.get(i)).draw().forEach(p -> fall.add(p))));
    }
}
