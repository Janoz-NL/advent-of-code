package com.janoz.aoc.y2024.day18;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.StopWatch;
import com.janoz.aoc.algorithms.Dijsktra;
import com.janoz.aoc.algorithms.PathFindingAlgorithm;
import com.janoz.aoc.geo.BoundingBox;
import com.janoz.aoc.geo.Point;

public class Day18 {

    static List<Point> bytes;
    static Predicate<Point> inBounds;

    public static void main(String[] args) {
        bytes = InputProcessor.asStream("inputs/2024/day18.txt", Point::parse).collect(Collectors.toList());
        Point target = new Point(70, 70);
        inBounds = new BoundingBox(Point.ORIGIN, target).inBoundsPredicate();

        StopWatch.start();
        int min = 1024;
        int max = bytes.size() - 1;
        while (true) {
            int i = min + ((max - min) / 2);
            if (new Dijsktra<>(validPredicate(i), Day18::getNeighbours, (p1, p2) -> 1L, p -> p.equals(target)).calculate(Point.ORIGIN) == null) {
                max = i;
            } else {
                min = i;
            }
            if (min + 1 == max) {
                System.out.println(bytes.get(max));
                break;
            }
        }
        StopWatch.stopPrint();
    }

    static Collection<Point> getNeighbours(Point p) {
        return p.streamNeighbour(inBounds).collect(Collectors.toList());
    }

    static BiFunction<Point,Point,Boolean> validPredicate(int time) {
        return (from,to) -> !bytes.subList(0,time+1).contains(to);
    }

    static void walkWithFalingBytes() {
        bytes = InputProcessor.asStream("inputs/2024/day18.txt", Point::parse).collect(Collectors.toList());
        Point target = new Point(70,70);
        inBounds = new BoundingBox(Point.ORIGIN, target).inBoundsPredicate();

        PathFindingAlgorithm<Point> pfa = new Dijsktra<>(
                Day18::validMove,
                Day18::getNeighbours,
                (p1,p2) -> 1L,
                p -> p.equals(target));

        pfa.calculate(Point.ORIGIN);
        System.out.println(pfa.getDistance(target));
    }

    static boolean validMove(Point from, Point to, long time) {
        return !bytes.subList(0,(int)time).contains(to);
    }

}
