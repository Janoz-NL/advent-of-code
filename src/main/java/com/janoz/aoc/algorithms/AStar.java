package com.janoz.aoc.algorithms;

import com.janoz.aoc.collections.AlwaysHashMap;
import com.janoz.aoc.geo.Point;
import com.janoz.aoc.geo.Utils;
import com.janoz.aoc.graphs.Node;

import java.util.Collection;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToLongBiFunction;

public class AStar<NODE> implements PathFindingAlgorithm<NODE>{

    private final HashMap<NODE,Long> distanceMap = new AlwaysHashMap<>(() -> Long.MAX_VALUE);
    private final BiFunction<NODE, NODE, Boolean> validMovePredicate;
    private final Function<NODE, Collection<NODE>> neighbourProducer;
    private final BiFunction<NODE, NODE, Long> distanceCalculator;
    private final Predicate<NODE> earlyOut;
    private final Function<NODE, Long> heuristic;

    private Consumer<NODE> algorithmCallback = node -> {};

    private AStar(BiFunction<NODE, NODE, Boolean> validMovePredicate, Function<NODE, Collection<NODE>> neighbourProducer, Predicate<NODE> earlyOut, Function<NODE, Long> heuristic) {
        this.validMovePredicate = validMovePredicate;
        this.distanceCalculator = (f,t) -> 1L;
        this.neighbourProducer = neighbourProducer;
        this.earlyOut = earlyOut;
        this.heuristic = heuristic;
    }

    @Override
    public Long calculate(Collection<NODE> starts) {
        PriorityQueue<Route<NODE>> heap = new PriorityQueue<>();
        starts.forEach(start-> addRoute(heap, new Route<>(start, 0L, heuristic.apply(start))));
        while (!heap.isEmpty()) {
            Route<NODE> source = heap.poll();
            algorithmCallback.accept(source.node);
            if (earlyOut.test(source.node)) return source.distance;
            if (source.distance > distanceMap.get(source.node)) continue; //already found a quicker way
            neighbourProducer.apply(source.node).stream()
                    .filter(n -> validMovePredicate.apply(source.node,n))
                    .map(n -> new Step(source,n))
                    .filter(Step::isPreferable)
                    .forEach(r -> addRoute(heap,r.to));
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

    @Override
    public Set<NODE> getVisited() {
        return distanceMap.keySet();
    }

    @Override
    public Collection<NODE> getNeighbours(NODE node) {
        return neighbourProducer.apply(node);
    }

    public void setAlgorithmCallback(Consumer<NODE> algorithmCallback) {
        this.algorithmCallback = algorithmCallback;
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

    /**
     * @param validRoutePredicate predicate indicating a move from src to dest is valid. Predicate should check for bounds
     * @param target target to find the path to
     */
    public static AStar<Point> for2DGrid(BiFunction<Point,Point, Boolean> validRoutePredicate, Point target) {
        return new AStar<>(validRoutePredicate, Point::neighbourCollection, p -> p.equals(target), p -> p.manhattanDistance(target));
    }

    /**
     * @param width Width of the field
     * @param height Height of the field
     * @param validRoutePredicate predicate indicating a move from src to dest is valid (no need to check bounds)
     * @param target target to find the path to
     */
    public static AStar<Point> for2DGrid(int width, int height, BiFunction<Point,Point, Boolean> validRoutePredicate, Point target) {
        return for2DGrid(Utils.boundsCheckWrapperForTo(width,height,validRoutePredicate), target);
    }

    /**
     *
     * @param target target to find the path to
     * @param heuristic AStar heuristic indicating the theoretical minimal cost between the nodes
     * @param <D> datatype contained in the node
     */
    public static <D> AStar<Node<D>> forNodes(Node<D> target, ToLongBiFunction<Node<D>, Node<D>> heuristic) {
        return new AStar<>((f,t) -> true, Node::reachable, n -> n == target, node -> heuristic.applyAsLong(node,target));
    }
}
