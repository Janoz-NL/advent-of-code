package com.janoz.aoc.y2024.day18;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.algorithms.AStar;
import com.janoz.aoc.algorithms.Dijsktra;
import com.janoz.aoc.algorithms.PathFindingAlgorithm;
import com.janoz.aoc.geo.BoundingBox;
import com.janoz.aoc.geo.Point;

public class Day18 {

    static List<Point> bytes;
    static Predicate<Point> inBounds;

    public static void main(String[] args) {
        bytes = InputProcessor.asStream("inputs/2024/day18.txt", Point::parse).collect(Collectors.toList());
        BoundingBox bb = new BoundingBox(bytes);
        Point target = bb.bottomRight();
        inBounds = bb.inBoundsPredicate();

        System.out.println("Part 1:" + AStar.for2DGrid(validMovePredicate(1024), target).calculate(Point.ORIGIN));

        int min = 1024;
        int max = bytes.size() - 1;
        while (true) {
            int i = min + ((max - min) / 2);
            if (AStar.for2DGrid(validMovePredicate(i), target).calculate(Point.ORIGIN) == null) {
                max = i;
            } else {
                min = i;
            }
            if (min + 1 == max) {
                System.out.println("Part 2:" + bytes.get(max));
                break;
            }
        }
    }

    static BiFunction<Point,Point,Boolean> validMovePredicate(int time) {
        return (from,to) -> inBounds.test(to) && !bytes.subList(0,time+1).contains(to);
    }

    static void walkWithFalingBytes() {
        bytes = InputProcessor.asStream("inputs/2024/day18.txt", Point::parse).collect(Collectors.toList());
        Point target = new Point(70,70);
        inBounds = new BoundingBox(Point.ORIGIN, target).inBoundsPredicate();

        PathFindingAlgorithm<Point> pfa = Dijsktra.for2DGrid(
                (from, to, time) -> inBounds.test(to) && !bytes.subList(0,(int) (long) time).contains(to),
                target::equals);

        pfa.calculate(Point.ORIGIN);
        System.out.println(pfa.getDistance(target));
    }
}
