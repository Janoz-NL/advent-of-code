package com.janoz.aoc.algorithms;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

import com.janoz.aoc.geo.Point;
import com.janoz.aoc.graphs.Node;

public class PFABuilder<NODE> {

    private Predicate<NODE> targetPredicate=n->false;
    private BiPredicate<NODE,NODE> validMovePredicate = (from,to) -> true;
    private BiFunction<NODE, NODE, Long> distanceCalculator;
    private Function<NODE, Collection<NODE>> neighbourProducer;
    private Function<NODE, Collection<NODE>> reversedMeighbourProducer;
    private Function<NODE, Function<NODE,Long>> aStarHeuristicConstructor;
    private Function<NODE, Long> aStarHeuristics;
    private BiPredicate<NODE,Long> validToAtDistancePredicate = (n,d) -> true;

    private PFABuilder() {
    }

    public PFABuilder<NODE> addValidMovePredicate(BiPredicate<NODE,NODE> extraValidMovePredicate) {
        this.validMovePredicate = validMovePredicate.and(extraValidMovePredicate);
        return this;
    }

    public PFABuilder<NODE> prependValidMovePredicate(BiPredicate<NODE,NODE> extraValidMovePredicate) {
        this.validMovePredicate = extraValidMovePredicate.and(validMovePredicate);
        return this;
    }

    public PFABuilder<NODE> addValidToPredicate(Predicate<NODE> toPredicate) {
        return addValidMovePredicate((from,to) -> toPredicate.test(to));
    }

    public PFABuilder<NODE> withValidToAtDistancePredicate(BiPredicate<NODE, Long> validToAtDistancePredicate) {
        this.validToAtDistancePredicate = validToAtDistancePredicate;
        return this;
    }

    public PFABuilder<NODE> withNeighbourProducer(Function<NODE, Collection<NODE>> neighbourProducer) {
        this.neighbourProducer = neighbourProducer;
        return this;
    }

    public PFABuilder<NODE> withDistanceCalculator(BiFunction<NODE, NODE, Long> distanceCalculator) {
        this.distanceCalculator = distanceCalculator;
        return this;
    }

    public PFABuilder<NODE> withTargetPredicate(Predicate<NODE> targetPredicate) {
        this.targetPredicate = targetPredicate;
        return this;
    }

    public PFABuilder<NODE> withTarget(NODE target) {
        this.aStarHeuristics = aStarHeuristicConstructor.apply(target);
        this.targetPredicate = target::equals;
        return this;
    }

    public PFABuilder<NODE> withObstacles(Collection<NODE> obstacles) {
        return addValidToPredicate((to) -> !obstacles.contains(to));
    }

    public Dijkstra<NODE> asDijkstra() {
        return new Dijkstra<>(
                validMovePredicate,
                neighbourProducer,
                reversedMeighbourProducer,
                distanceCalculator,
                validToAtDistancePredicate,
                targetPredicate);
    }

    public BFS<NODE> asBFS() {
        return new BFS<>(
                validMovePredicate,
                neighbourProducer,
                reversedMeighbourProducer,
                validToAtDistancePredicate,
                targetPredicate);
    }

    public AStar<NODE> asAStar() {
        if (aStarHeuristics == null) {
            throw new RuntimeException("A-Star algorithm needs an heuristics to function.");
        }
        return new AStar<>(validMovePredicate,neighbourProducer,reversedMeighbourProducer,validToAtDistancePredicate,targetPredicate,aStarHeuristics);
    }

    public AStar<NODE> asAStar(Function<NODE, Long> heuristic) {
        return new AStar<>(validMovePredicate,neighbourProducer,reversedMeighbourProducer,validToAtDistancePredicate,targetPredicate,heuristic);
    }

    public static PFABuilder<Point> forPoints(int width, int height) {
        PFABuilder<Point> builder = forPoints();
        builder.addValidToPredicate(Point.boundsPredicate(width,height));
        return builder;
    }

    public static PFABuilder<Point> forPoints() {
        PFABuilder<Point> builder = new PFABuilder<>();
        builder.neighbourProducer = Point::neighbourCollection;
        builder.reversedMeighbourProducer = Point::neighbourCollection;
        builder.distanceCalculator = (from, to) -> 1L;
        builder.aStarHeuristicConstructor = target -> target::manhattanDistance;
        return builder;
    }

    public static <T> PFABuilder<Node<T>> forNodes(Class<T> dataClass) {
        PFABuilder<Node<T>> builder = new PFABuilder<>();
        builder.neighbourProducer = Node::reachable;
        builder.reversedMeighbourProducer = Node::reverseRachable;
        builder.distanceCalculator = (from,to) -> from.getTo(to).getLength();
        return builder;
    }

    public static <T> PFABuilder<T> forType(Class<T> nodeClass) {
        return new PFABuilder<>();
    }

}
