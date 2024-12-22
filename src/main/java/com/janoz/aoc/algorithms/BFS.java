package com.janoz.aoc.algorithms;

import com.janoz.aoc.geo.Point;
import com.janoz.aoc.geo.Utils;
import com.janoz.aoc.graphs.Node;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class BFS<NODE> implements PathFindingAlgorithm<NODE> {

    private final Map<NODE,Long> distanceMap= new HashMap<>();
    private final BiFunction<NODE, NODE, Boolean> validMovePredicate;
    private final Function<NODE, Collection<NODE>> neighbourProducer;
    private final Predicate<NODE> earlyOut;
    private Consumer<NODE> algorithmCallback = node -> {};

    public BFS(BiFunction<NODE, NODE, Boolean> validMovePredicate, Function<NODE, Collection<NODE>> neighbourProducer, Predicate<NODE> earlyOut) {
        this.validMovePredicate = validMovePredicate;
        this.neighbourProducer = neighbourProducer;
        this.earlyOut = earlyOut;
    }

    @Override
    public Long calculate(Collection<NODE> starts) {
        Queue<NODE> queue = new LinkedList<>();
        starts.forEach(node -> {
            queue.add(node);
            distanceMap.put(node,0L);
        });
        while (!queue.isEmpty()) {
            NODE node = queue.poll();
            algorithmCallback.accept(node);
            long newDistance = distanceMap.get(node) + 1L;
            if (earlyOut.test(node)) return distanceMap.get(node);
            neighbourProducer.apply(node).stream()
                    .filter(n -> !distanceMap.containsKey(n)) // not visited
                    .filter(n -> validMovePredicate.apply(node, n)) // valid move
                    .forEach(n -> {
                        distanceMap.put(n, newDistance);
                        queue.add(n);
                    });
        }
        return null;
    }

    @Override
    public Long getDistance(NODE node) {
        return distanceMap.get(node);
    }

    @Override
    public Set<NODE> getVisited() {
        return distanceMap.keySet();
    }

    @Override
    public Collection<NODE> getNeighbours(NODE node) {
        return neighbourProducer.apply(node);
    }

    @Override
    public void setAlgorithmCallback(Consumer<NODE> algorithmCallback) {
        this.algorithmCallback = algorithmCallback;
    }

    public static BFS<Point> forPoints(int width, int height, BiFunction<Point,Point, Boolean> validRoutePredicate) {
        return forPoints(width, height, validRoutePredicate, p -> false);
    }

    public static BFS<Point> forPoints(int width, int height, BiFunction<Point,Point, Boolean> validRoutePredicate, Predicate<Point> earlyOut) {
        return new BFS<>(Utils.boundsCheckWrapperForTo(width,height,validRoutePredicate), Point::neighbourCollection, earlyOut);
    }

    public static <D> BFS<Node<D>> forNodes() {
        return new BFS<>((f, t) -> true, Node::reachable, n -> false);
    }
}
