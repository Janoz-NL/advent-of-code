package com.janoz.aoc.geo;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.function.BiFunction;
import java.util.function.Function;

public class DijsktraGrid {

    private BiFunction<Point, Point, Boolean> validMovePredicate;
    private Function<Point, Point[]> neighbourProducer = Point::neighbours;

    long[][] distanceGrid;

    public DijsktraGrid(int width, int height, BiFunction<Point, Point, Boolean> validMovePredicate) {
        this.validMovePredicate = validMovePredicate;
        distanceGrid = new long[height][width];
    }

    public void calculateFromEnd(Point end) {
        for (long[] longs : distanceGrid) {
            Arrays.fill(longs, Long.MAX_VALUE);
        }
        PriorityQueue<DistancePoint> heap = new PriorityQueue<>();
        distanceGrid[end.y][end.x] = 0;
        heap.add(new DistancePoint(end, 0));

        while (!heap.isEmpty()) {
            DistancePoint p = heap.poll();
            if (p.distance != distanceGrid[p.point.y][p.point.x]) break;
            Point[] neighbours =neighbourProducer.apply(p.point);
            for (Point neighbour : neighbours) {
                if (neighbour.x<0 || neighbour.y<0 || neighbour.x>= distanceGrid[0].length  || neighbour.y>= distanceGrid.length) break;
                if (validMovePredicate.apply(neighbour, p.point)) {
                    if (distanceGrid[neighbour.y][neighbour.x] > p.distance + 1) {
                        distanceGrid[neighbour.y][neighbour.x] = p.distance + 1;
                        heap.add(new DistancePoint(neighbour, p.distance + 1));
                    }
                }
            }
        }
    }

    public long getDistanceToEnd(Point start) {
        return distanceGrid[start.y][start.x];
    }

    private static class DistancePoint implements Comparable<DistancePoint> {

        Point point;
        int distance;

        @Override
        public int compareTo(DistancePoint o) {
            return distance - o.distance;
        }

        public DistancePoint(Point p, int data) {
            point = p;
            this.distance = data;
        }
    }
}
