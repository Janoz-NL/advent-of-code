package com.janoz.aoc.y2022.day18;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.StopWatch;
import com.janoz.aoc.collections.LongRange;
import com.janoz.aoc.geo.Point3D;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class Day18 {

    public static void main(String[] args) {
        String file = "inputs/2022/day18.txt";

        StopWatch.start();
        Set<Point3D> points = InputProcessor.asStream(file, Point3D::parse).collect(Collectors.toSet());
        System.out.println(surface(points));

        LongRange xRange = new LongRange(points.stream().mapToLong(p -> p.x).summaryStatistics()).grow();
        LongRange yRange = new LongRange(points.stream().mapToLong(p -> p.y).summaryStatistics()).grow();
        LongRange zRange = new LongRange(points.stream().mapToLong(p -> p.z).summaryStatistics()).grow();

        Set<Point3D> encasement = encase(points,xRange,yRange,zRange);

        System.out.println(
                        surface(encasement) -
                        getOuterSurface(xRange.size(), yRange.size(), zRange.size()));
        StopWatch.stopPrint();
    }

    private static long getOuterSurface(long length, long width, long height) {
        return 2 * (length * width + length * height + width * height);
    }

    private static long surface(Set<Point3D> points) {
        return points.stream().mapToLong(p -> p.neighbours().filter(n -> !points.contains(n)).count()).sum();
    }

    static Set<Point3D> encase(Set<Point3D> shape, LongRange xRange, LongRange yRange, LongRange zRange) {
        Point3D start = new Point3D(xRange.getMin(), yRange.getMin(), zRange.getMin());

        Set<Point3D> encasement = new HashSet<>();
        encasement.add(start);
        Queue<Point3D> queue = new LinkedList<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            Point3D current = queue.poll();
            current.neighbours()
                    .filter(n -> xRange.inRange(n.x))
                    .filter(n -> yRange.inRange(n.y))
                    .filter(n -> zRange.inRange(n.z))
                    .filter(n -> !encasement.contains(n))
                    .filter(n -> !shape.contains(n))
                    .forEach(n -> {
                        encasement.add(n);
                        queue.add(n);
                    });
        }
        return encasement;
    }
}
