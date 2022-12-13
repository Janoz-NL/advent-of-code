package com.janoz.aoc.algorithms;

import com.janoz.aoc.collections.AlwaysHashMap;
import com.janoz.aoc.geo.Point;
import com.janoz.aoc.geo.Utils;
import com.janoz.aoc.graphs.Node;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class Dijsktra<NODE> {

    private final Map<NODE,Long> distanceMap= new AlwaysHashMap<>(() -> Long.MAX_VALUE);
    private final BiFunction<NODE, NODE, Boolean> validMovePredicate;
    private final Function<NODE, Collection<NODE>> neighbourProducer;
    private final BiFunction<NODE, NODE, Long> distanceCalculator;
    private final Predicate<NODE> earlyOut;

    public Dijsktra(BiFunction<NODE, NODE, Boolean> validMovePredicate, Function<NODE, Collection<NODE>> neighbourProducer, BiFunction<NODE, NODE, Long> distanceCalculator, Predicate<NODE> earlyOut) {
        this.validMovePredicate = validMovePredicate;
        this.neighbourProducer = neighbourProducer;
        this.distanceCalculator = distanceCalculator;
        this.earlyOut = earlyOut;
    }

    public long calculate(NODE start) {
        return calculate(Collections.singletonList(start));
    }

    public long calculate(List<NODE> starts) {
        PriorityQueue<Route<NODE>> heap = new PriorityQueue<>();
        starts.forEach(node -> {
            Route<NODE> route = new Route<>(node, 0L);
            addRoute(heap, route);
        });
        while (!heap.isEmpty()) {
            Route<NODE> source = heap.poll();
            if (earlyOut.test(source.node)) return source.distance;
            if (source.distance != distanceMap.get(source.node)) continue; //already found a quicker way
            neighbourProducer.apply(source.node).stream()
                    .filter(n -> validMovePredicate.apply(source.node, n))
                    .map(n -> new Step(source,n))
                    .filter(Step::isPreferable)
                    .forEach(r -> r.addTo(heap));
        }
        return -1;
    }

    private void addRoute(PriorityQueue<Route<NODE>> heap, Route<NODE> route) {
        distanceMap.put(route.node, route.distance);
        heap.add(route);
    }

    public long getDistance(NODE node) {
        return distanceMap.get(node);
    }

    private class Step {
        Route<NODE> from;
        Route<NODE> to;

        Step(Route<NODE> from, NODE to) {
            this.from = from;
            this.to = new Route<>(to, from.distance + distanceCalculator.apply(from.node, to));
        }

        boolean isPreferable() {
            return to.distance < distanceMap.get(to.node);
        }

        void addTo(Collection<Route<NODE>> heap) {
            distanceMap.put(to.node, to.distance);
            heap.add(to);
        }
    }

    private static class Route<NODE> implements Comparable<Route<NODE>> {
        NODE node;
        long distance;

        @Override
        public int compareTo(Route o) {
            return (int)Math.signum(distance - o.distance);
        }

        public Route(NODE p, long data) {
            node = p;
            this.distance = data;
        }
    }

    public static Dijsktra<Point> for2DGrid(int width, int height, BiFunction<Point,Point, Boolean> validRoutePredicate) {
        return new Dijsktra<>(Utils.boundsCheckWrapperForTo(width,height,validRoutePredicate), Point::neighbourCollection,(f, t) -> 1L, n -> false);
    }

    public static Dijsktra<Point> for2DGrid(int width, int height, BiFunction<Point,Point, Boolean> validRoutePredicate, Predicate<Point> earlyOut) {
        return new Dijsktra<>(Utils.boundsCheckWrapperForTo(width,height,validRoutePredicate), Point::neighbourCollection,(f,t) -> 1L, earlyOut);
    }

    public static Dijsktra<Node> forNodes() {
        return new Dijsktra<>((f,t) -> true, Node::reachable, (f,t) -> f.getTo(t).getLength(), n -> false);
    }

    public static Dijsktra<Node> forNodes(Node target) {
        return new Dijsktra<>((f,t) -> true, Node::reachable, (f,t) -> f.getTo(t).getLength(), n -> n == target);
    }
}