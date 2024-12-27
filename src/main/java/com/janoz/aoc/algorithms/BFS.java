package com.janoz.aoc.algorithms;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class BFS<NODE> implements PathFindingAlgorithm<NODE> {

    private final Map<NODE,Long> distanceMap= new HashMap<>();
    private final BiPredicate<NODE, NODE> validMovePredicate;
    private final Function<NODE, Collection<NODE>> neighbourProducer;
    private final Function<NODE, Collection<NODE>> reversedMeighbourProducer;

    private final BiPredicate<NODE, Long> validToAtDistancePredicate;

    private final Predicate<NODE> earlyOut;
    private Consumer<NODE> algorithmCallback = node -> {};

    BFS(BiPredicate<NODE, NODE> validMovePredicate, Function<NODE, Collection<NODE>> neighbourProducer, Function<NODE, Collection<NODE>> reversedMeighbourProducer, BiPredicate<NODE, Long> validToAtDistancePredicate, Predicate<NODE> earlyOut) {
        this.validMovePredicate = validMovePredicate;
        this.neighbourProducer = neighbourProducer;
        this.reversedMeighbourProducer = reversedMeighbourProducer;
        this.validToAtDistancePredicate = validToAtDistancePredicate;
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
                    .filter(n -> validMovePredicate.test(node, n)) // valid move
                    .filter(n -> validToAtDistancePredicate.test(node,newDistance))
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
    public Collection<NODE> getReachableFrom(NODE node) {
        return neighbourProducer.apply(node);
    }

    @Override
    public Collection<NODE> getReachableTo(NODE node) {
        return reversedMeighbourProducer.apply(node);
    }

    @Override
    public void setAlgorithmCallback(Consumer<NODE> algorithmCallback) {
        this.algorithmCallback = algorithmCallback;
    }
}
