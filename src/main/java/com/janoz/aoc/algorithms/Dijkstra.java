package com.janoz.aoc.algorithms;

import com.janoz.aoc.collections.AlwaysHashMap;

import java.util.Collection;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Dijkstra<NODE> implements PathFindingAlgorithm<NODE> {

    private final Map<NODE,Long> distanceMap= new AlwaysHashMap<>(() -> Long.MAX_VALUE);
    private final BiPredicate<NODE, NODE> validMovePredicate;
    private final Function<NODE, Collection<NODE>> neighbourProducer;
    private final Function<NODE, Collection<NODE>> reversedMeighbourProducer;
    private final BiFunction<NODE, NODE, Long> distanceCalculator;
    private final Predicate<Step> validStepPredicate;
    private final Predicate<NODE> earlyOut;
    private Consumer<NODE> algorithmCallback = node -> {};


    Dijkstra(BiPredicate<NODE, NODE> validMovePredicate, Function<NODE, Collection<NODE>> neighbourProducer, Function<NODE, Collection<NODE>> reversedMeighbourProducer, BiFunction<NODE, NODE, Long> distanceCalculator, BiPredicate<NODE, Long> validToAtDistancePredicate, Predicate<NODE> earlyOut) {
        this.validMovePredicate = validMovePredicate;
        this.neighbourProducer = neighbourProducer;
        this.reversedMeighbourProducer = reversedMeighbourProducer;
        this.distanceCalculator = distanceCalculator;
        this.validStepPredicate = step -> validToAtDistancePredicate.test(step.to.node,step.to.distance);
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
            algorithmCallback.accept(source.node);
            if (earlyOut.test(source.node)) return source.distance;
            if (source.distance != distanceMap.get(source.node)) continue; //already found a quicker way
            neighbourProducer.apply(source.node).stream()
                    .filter(n -> validMovePredicate.test(source.node, n))
                    .map(n -> new Step(source,n))
                    .filter(Step::isPreferable)
                    .filter(validStepPredicate)
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
}