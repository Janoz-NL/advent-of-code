package com.janoz.aoc.y2024.day6;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.geo.BoundingBox;
import com.janoz.aoc.geo.Direction;
import com.janoz.aoc.geo.Point;

public class Day6 {

    static Set<Point> obstacles;
    static Predicate<Point> inbounds;
    static Set<Point> visited;
    static Point start;
    static Set<Point> turnpoints = new HashSet<>();

    public static void main(String[] args) {
        init("inputs/2024/day06.txt");
        visited = walk();
        System.out.println(visited.size());
        System.out.println(possibleLoops());
    }

    static Set<Point> walk() {
        return walk(null);
    }

    /**
     * @param obstacle extra obstacle or null for part 1
     * @return a set of visited nodes, or null if the guard is in a loop
     */
    static Set<Point> walk(Point obstacle) {
        Predicate<Point> blocked = obstacle==null?p->false:obstacle::equals;
        Collection<Point> extraTurnPoints = obstacle==null?Collections.emptyList():obstacle.neighbourCollection();
        Set<Point> visited = new LinkedHashSet<>();
        Point curpos = start;
        Direction direction = Direction.NORTH;
        visited.add(curpos);
        List<Point> path = new ArrayList<>();
        List<Direction> directions = new ArrayList<>();
        path.add(curpos);
        directions.add(direction);
        while(true) {
            if (obstacle != null && inLoop(path, directions, extraTurnPoints)) {
                return null;
            }
            Point nextPos = curpos.translate(direction);
            if (!inbounds.test(nextPos)) return visited;
            if (!obstacles.contains(nextPos) && !blocked.test(nextPos)) {
                visited.add(nextPos);
                path.add(nextPos);
                directions.add(direction);
                curpos = nextPos;
            } else {
                direction = direction.rotateCW();
                directions.set(directions.size()-1, direction);
            }
        }
    }

    /**
     * @return true when last point added created a loop
     */
    static boolean inLoop(List<Point> path, List<Direction> direction, Collection<Point> extraTurns) {
        Point pos = path.get(path.size()-1);
        if (!turnpoints.contains(pos) && !extraTurns.contains(pos)) return false;
        Direction dir = direction.get(path.size()-1);
        for (int i=0; i<path.size()-1; i++) {
            if (path.get(i).equals(pos) && direction.get(i).equals(dir)) return true;
        }
        return false;
    }

    static void init(String file) {
        obstacles = new HashSet<>();
        inbounds = BoundingBox.readGrid(InputProcessor.asIterator(file), (p,c) -> {
            if (c == '^') start = p;
            if (c == '#') obstacles.add(p);
        }).inBoundsPredicate();
        turnpoints = obstacles.stream()
                .flatMap(p -> p.neighbourCollection().stream())
                .filter(inbounds).collect(Collectors.toSet());
    }

    static long possibleLoops() {
        return visited.parallelStream()
                .filter(p -> !p.equals(start))
                .filter(o -> walk(o) == null).count();
    }
}
