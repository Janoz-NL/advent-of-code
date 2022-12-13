package com.janoz.aoc.algorithms;

import com.janoz.aoc.collections.AlwaysHashMap;
import com.janoz.aoc.geo.Point;
import com.janoz.aoc.geo.Utils;
import com.janoz.aoc.graphs.Node;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class AStar<NODE> implements PathFindingAlgorithm<NODE>{

    private final HashMap<NODE,Long> distanceMap = new AlwaysHashMap<>(() -> Long.MAX_VALUE);
    private final BiFunction<NODE, NODE, Boolean> validMovePredicate;
    private final Function<NODE, Collection<NODE>> neighbourProducer;
    private final BiFunction<NODE, NODE, Long> distanceCalculator;
    private final Predicate<NODE> earlyOut;
    private final Function<NODE, Long> heuristic;

    private AStar(BiFunction<NODE, NODE, Boolean> validMovePredicate, Function<NODE, Collection<NODE>> neighbourProducer, Predicate<NODE> earlyOut, Function<NODE, Long> heuristic) {
        this.validMovePredicate = validMovePredicate;
        this.distanceCalculator = (f,t) -> 1L;
        this.neighbourProducer = neighbourProducer;
        this.earlyOut = earlyOut;
        this.heuristic = heuristic;
    }

    @Override
    public long calculate(Collection<NODE> starts) {
        if (starts.size() != 1) throw new RuntimeException("A* only implemented for single start");
        NODE start = starts.iterator().next();
        Route<NODE> route = new Route<>(start, 0L, heuristic.apply(start));
        PriorityQueue<Route<NODE>> heap = new PriorityQueue<>();
        addRoute(heap, route);
        while (!heap.isEmpty()) {
            Route<NODE> source = heap.poll();
            if (earlyOut.test(source.node)) return source.distance;
            if (source.distance != distanceMap.get(source.node)) continue; //already found a quicker way
            neighbourProducer.apply(source.node).stream()
                    .filter(n -> validMovePredicate.apply(source.node,n))
                    .map(n -> new Step(source,n))
                    .filter(Step::isPreferable)
                    .forEach(r -> addRoute(heap,r.to));
        }
        return -1;
    }

    @Override
    public long getDistance(NODE node) {
        return distanceMap.get(node);
    }

    private void addRoute(PriorityQueue<Route<NODE>> heap, Route<NODE> route) {
        distanceMap.put(route.node, route.distance);
        heap.add(route);
    }

    private class Step {
        Route<NODE> from;
        Route<NODE> to;

        Step(Route<NODE> from, NODE to) {
            this.from = from;
            this.to = new Route<>(to, from.distance + distanceCalculator.apply(from.node, to),heuristic.apply(to));
        }

        boolean isPreferable() {
            return to.distance < distanceMap.get(to.node);
        }
    }

    private static class Route<NODE> implements Comparable<Route<NODE>> {
        NODE node;
        long distance;
        long expectedDistance;

        @Override
        public int compareTo(Route o) {
            //in case of same expected, ignore heuristic.
            int result = (int)Math.signum(expectedDistance - o.expectedDistance);
            return result == 0?(int)(distance - o.distance):result;
        }

        public Route(NODE p, long distanceUntilNow, long minimumDistanceToTarget) {
            node = p;
            this.distance = distanceUntilNow;
            this.expectedDistance = distanceUntilNow + minimumDistanceToTarget;
        }
    }

    public static AStar<Point> for2DGrid(int width, int height, BiFunction<Point,Point, Boolean> validRoutePredicate, Point target) {
        return new AStar<>(Utils.boundsCheckWrapperForTo(width,height,validRoutePredicate), Point::neighbourCollection, p -> p.equals(target), p -> p.manhattanDistance(target));
    }

    public static Dijsktra<Node> forNodes(Node target) {
        return new Dijsktra<>((f,t) -> true, Node::reachable, (f,t) -> f.getTo(t).getLength(), n -> n == target);
    }


}
