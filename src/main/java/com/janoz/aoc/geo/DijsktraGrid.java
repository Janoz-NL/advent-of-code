package com.janoz.aoc.geo;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.function.BiFunction;
import java.util.function.Function;

public class DijsktraGrid {

    private final GrowingGrid<Long> distanceGrid= new GrowingGrid<>(Long.MAX_VALUE);
    private final BiFunction<Point, Point, Boolean> validMovePredicate;
    private final Function<Point, Point[]> neighbourProducer = Point::neighbours;

    public DijsktraGrid(int width, int height, BiFunction<Point, Point, Boolean> validMovePredicate) {
        this.validMovePredicate = (f,t) -> f.x>=0 && f.y>=0 && f.x<width && f.y<height && validMovePredicate.apply(f,t);
    }

    public void calculateFromEnd(Point end) {
        PriorityQueue<DistancePoint> heap = new PriorityQueue<>(Collections.singleton(new DistancePoint(end, end.putOn(distanceGrid,0L))));
        while (!heap.isEmpty()) {
            DistancePoint target = heap.poll();
            if (target.distance != distanceGrid.get(target.point)) break; //already found a quicker way
            Arrays.stream(neighbourProducer.apply(target.point))
                    .filter(n -> validMovePredicate.apply(n,target.point))
                    .filter(n -> distanceGrid.get(n) > target.distance + 1)
                    .forEach(n -> heap.add(new DistancePoint(n, n.putOn(distanceGrid, target.distance + 1))));
        }
    }

    public long getDistanceToEnd(Point start) {
        return distanceGrid.get(start);
    }

    private static class DistancePoint implements Comparable<DistancePoint> {
        Point point;
        long distance;

        @Override
        public int compareTo(DistancePoint o) {
            return (int)Math.signum(distance - o.distance);
        }

        public DistancePoint(Point p, long data) {
            point = p;
            this.distance = data;
        }
    }
}
