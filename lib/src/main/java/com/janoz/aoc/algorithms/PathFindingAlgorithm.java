package com.janoz.aoc.algorithms;

import java.awt.Color;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import com.janoz.aoc.geo.HistoGrid;
import com.janoz.aoc.geo.Point;
import com.janoz.aoc.geo.ReadOnlyGrid;

public interface PathFindingAlgorithm<NODE> {

    default Long calculate(NODE start) {
        return calculate(Collections.singletonList(start));
    }

    Long calculate(Collection<NODE> starts);

    Long getDistance(NODE node);

    Set<NODE> getVisited();

    Collection<NODE> getReachableFrom(NODE node);

    Collection<NODE> getReachableTo(NODE node);

    void setAlgorithmCallback(Consumer<NODE> algorithmCallback);

    /**
     * Path is backtracking so assumes a two-way maze
     * @param end eindpoint of the path
     * @return a path from start to end. If the path has more starts, one of the closest is returned
     */
    default List<NODE> getPath(NODE end) {
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

    default <T> Map<T, Set<NODE>> algoStateToDatamap(NODE p, T visitedVal, T pathVal) {
        Map<T, Set<NODE>> data = new LinkedHashMap<>();
        data.put(visitedVal, new HashSet<>(getVisited()));
        data.put(pathVal, new HashSet<>(getPath(p)));
        data.get(visitedVal).removeAll(data.get(pathVal));
        return data;
    }

    static Long renderingGridPathFinding(int width, int height, PathFindingAlgorithm<Point> algorithm, Point start, Set<Point> obstacles, Consumer<ReadOnlyGrid<Color>> gridConsumer) {
        algorithm.setAlgorithmCallback(p -> {
            HistoGrid<Color> grid = new HistoGrid<>(width, height, algorithm.algoStateToDatamap(p, Color.LIGHT_GRAY, Color.ORANGE));
            grid.put(p,Color.GREEN);
            grid.put(obstacles, Color.GRAY);
            gridConsumer.accept(grid);
        });
        return algorithm.calculate(start);
    }
}
