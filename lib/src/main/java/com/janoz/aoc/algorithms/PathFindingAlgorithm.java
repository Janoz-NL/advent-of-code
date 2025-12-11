package com.janoz.aoc.algorithms;

import java.awt.Color;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;

import com.janoz.aoc.geo.HistoGrid;
import com.janoz.aoc.geo.Point;
import com.janoz.aoc.geo.ReadOnlyGrid;

public abstract class PathFindingAlgorithm<NODE> {

    protected final BiPredicate<NODE, NODE> validMovePredicate;
    protected final Function<NODE, Collection<NODE>> neighbourProducer;

    public PathFindingAlgorithm(BiPredicate<NODE, NODE> validMovePredicate, Function<NODE, Collection<NODE>> neighbourProducer) {
        this.validMovePredicate = validMovePredicate;
        this.neighbourProducer = neighbourProducer;
    }

    public Long calculate(NODE start) {
        return calculate(Collections.singletonList(start));
    }

    public abstract Long calculate(Collection<NODE> starts);

    public abstract Long getDistance(NODE node);

    public abstract Set<NODE> getVisited();

    public abstract Collection<NODE> getReachableFrom(NODE node);

    public abstract Collection<NODE> getReachableTo(NODE node);

    public abstract void setAlgorithmCallback(Consumer<NODE> algorithmCallback);

    /**
     * Path is backtracking so assumes a two-way maze
     * @param end eindpoint of the path
     * @return a path from start to end. If the path has more starts, one of the closest is returned
     */
    public List<NODE> getPath(NODE end) {
        LinkedList<NODE> path = new LinkedList<>();
        long distance = getDistance(end);
        NODE current = end;
        path.add(end);
        while (distance > 0) {
            for (NODE candidate : getReachableTo(current)) {
                long nextDistance = Optional.ofNullable(getDistance(candidate)).orElse(Long.MAX_VALUE);
                if (nextDistance < distance) {
                    current = candidate;
                    distance = nextDistance;
                }
                path.addFirst(current);
            }
        }
        return path;
    }

    public long nrOfPaths(NODE start, NODE end) {
        return new InnerPathAccumulator(start,end).nrOfPaths(start);
    }

    private class InnerPathAccumulator {
        Map<NODE, Long> cache = new HashMap<>();
        NODE end;

        InnerPathAccumulator(NODE start, NODE end) {
            cache.put(end, 1L);
            this.end = end;
        }

        long nrOfPaths(NODE start) {
            if (cache.containsKey(start)) return cache.get(start);
            if (start == end) return 1;
            long paths = neighbourProducer.apply(start).stream().mapToLong(this::nrOfPaths).sum();
            cache.put(start,paths);
            return paths;

        }
    }


    public <T> Map<T, Set<NODE>> algoStateToDatamap(NODE p, T visitedVal, T pathVal) {
        Map<T, Set<NODE>> data = new LinkedHashMap<>();
        data.put(visitedVal, new HashSet<>(getVisited()));
        data.put(pathVal, new HashSet<>(getPath(p)));
        data.get(visitedVal).removeAll(data.get(pathVal));
        return data;
    }

    public static Long renderingGridPathFinding(int width, int height, PathFindingAlgorithm<Point> algorithm, Point start, Set<Point> obstacles, Consumer<ReadOnlyGrid<Color>> gridConsumer) {
        algorithm.setAlgorithmCallback(p -> {
            HistoGrid<Color> grid = new HistoGrid<>(width, height, algorithm.algoStateToDatamap(p, Color.LIGHT_GRAY, Color.ORANGE));
            grid.put(p,Color.GREEN);
            grid.put(obstacles, Color.GRAY);
            gridConsumer.accept(grid);
        });
        return algorithm.calculate(start);
    }
}
