package com.janoz.aoc.algorithms;

import com.janoz.aoc.collections.AlwaysHashMap;
import com.janoz.aoc.geo.Point;
import com.janoz.aoc.geo.Utils;
import com.janoz.aoc.graphs.Node;

import java.util.Collection;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import org.assertj.core.util.TriFunction;

public class Dijsktra<NODE> implements PathFindingAlgorithm<NODE> {

    private final Map<NODE,Long> distanceMap= new AlwaysHashMap<>(() -> Long.MAX_VALUE);
    private final BiFunction<NODE, NODE, Boolean> validMovePredicate;
    private final Function<NODE, Collection<NODE>> neighbourProducer;
    private final BiFunction<NODE, NODE, Long> distanceCalculator;
    private final Predicate<NODE> earlyOut;

    public Dijsktra(TriFunction<NODE, NODE, Long, Boolean> validMovePredicate, Function<NODE, Collection<NODE>> neighbourProducer, BiFunction<NODE, NODE, Long> distanceCalculator, Predicate<NODE> earlyOut) {
        this.validMovePredicate = (from,to) ->{
            long distance = getDistance(from);
            distance = distance + distanceCalculator.apply(from,to);
            return validMovePredicate.apply(from,to,distance);
        };
        this.neighbourProducer = neighbourProducer;
        this.distanceCalculator = distanceCalculator;
        this.earlyOut = earlyOut;


    }

    public Dijsktra(BiFunction<NODE, NODE, Boolean> validMovePredicate, Function<NODE, Collection<NODE>> neighbourProducer, BiFunction<NODE, NODE, Long> distanceCalculator, Predicate<NODE> earlyOut) {
        this.validMovePredicate = validMovePredicate;
        this.neighbourProducer = neighbourProducer;
        this.distanceCalculator = distanceCalculator;
        this.earlyOut = earlyOut;
    }

    @Override
    public Long calculate(Collection<NODE> starts) {
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
        return null;
    }

    @Override
    @SuppressWarnings("Java8MapApi")
    public Long getDistance(NODE node) {
        if (distanceMap.containsKey(node))
            return distanceMap.get(node);
        else return null;
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

    /**
     * @param validRoutePredicate predicate indicating a move from src to dest is valid. Predicate should check for bounds
     */
    public static Dijsktra<Point> for2DGrid(BiFunction<Point,Point, Boolean> validRoutePredicate) {
        return for2DGrid(validRoutePredicate, n -> false);
    }

    /**
     * @param validRoutePredicate predicate indicating a move from src to dest is valid at a specific step. Predicate should check for bounds
     * @param earlyOut predicate indicating if pathfinding is finished
     */
    public static Dijsktra<Point> for2DGrid(TriFunction<Point,Point, Long, Boolean> validRoutePredicate, Predicate<Point> earlyOut) {
        return new Dijsktra<>(validRoutePredicate, Point::neighbourCollection, (f,t)->1L , earlyOut);
    }

    /**
     * @param validRoutePredicate predicate indicating a move from src to dest is valid. Predicate should check for bounds
     * @param earlyOut predicate indicating if pathfinding is finished
     */
    public static Dijsktra<Point> for2DGrid(BiFunction<Point,Point, Boolean> validRoutePredicate, Predicate<Point> earlyOut) {
        return new Dijsktra<>(validRoutePredicate, Point::neighbourCollection,(f, t) -> 1L, earlyOut);
    }

    /**
     *
     * @param width Width of the field
     * @param height Height of the field
     * @param validRoutePredicate predicate indicating a move from src to dest is valid (no need to check bounds)
     */
    public static Dijsktra<Point> for2DGrid(int width, int height, BiFunction<Point,Point, Boolean> validRoutePredicate) {
        return for2DGrid(Utils.boundsCheckWrapperForTo(width,height,validRoutePredicate));
    }

    /**
     *
     * @param width Width of the field
     * @param height Height of the field
     * @param validRoutePredicate predicate indicating a move from src to dest is valid (no need to check bounds)
     * @param earlyOut predicate indicating if pathfinding is finished
     */
    public static Dijsktra<Point> for2DGrid(int width, int height, BiFunction<Point,Point, Boolean> validRoutePredicate, Predicate<Point> earlyOut) {
        return for2DGrid(Utils.boundsCheckWrapperForTo(width,height,validRoutePredicate), earlyOut);
    }

    /**
     *
     * @param inbounds predicate to determine if a point is in bounds
     * @param validRoutePredicate predicate indicating a move from src to dest is valid (no need to check bounds)
     * @param earlyOut predicate indicating if pathfinding is finished
     */
    public static Dijsktra<Point> for2DGrid(Predicate<Point> inbounds, BiFunction<Point,Point, Boolean> validRoutePredicate, Predicate<Point> earlyOut) {
        return for2DGrid(Utils.boundsCheckWrapperForTo(inbounds,validRoutePredicate), earlyOut);
    }

    public static <D> Dijsktra<Node<D>> forNodes() {
        return new Dijsktra<>((f,t) -> true, Node::reachable, (f,t) -> f.getTo(t).getLength(), n -> false);
    }

    public static <D> Dijsktra<Node<D>> forNodes(Node<D> target) {
        return new Dijsktra<>((f,t) -> true, Node::reachable, (f,t) -> f.getTo(t).getLength(), n -> n == target);
    }
}