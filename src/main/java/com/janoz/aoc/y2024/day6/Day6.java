package com.janoz.aoc.y2024.day6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.geo.Direction;
import com.janoz.aoc.geo.Point;

public class Day6 {

    static List<String> map;
    static int width;
    static int height;
    static Predicate<Point> inbounds;
    static Set<Point> visited;
    static Point start;
    static Set<Point> turnpoints = new HashSet<>();

    public static void main(String[] args) {
        map = InputProcessor.asStream("inputs/2024/day06.txt").collect(Collectors.toList());
        init();
        visited = walk();
        System.out.println(visited.size());
        placeObstacles();
    }

    static Set<Point> walk() {
        return walk(null);
    }

    static Set<Point> walk(Point newObstacle) {
        Predicate<Point> blocked = newObstacle==null?p->false:newObstacle::equals;
        Collection<Point> extraTurnPoints = newObstacle==null?Collections.emptyList():newObstacle.neighbourCollection();
        Set<Point> visited = new LinkedHashSet<>();
        Point curpos = start;
        Direction direction = Direction.NORTH;
        visited.add(curpos);
        List<Point> path = new ArrayList<>();
        List<Direction> directions = new ArrayList<>();
        path.add(curpos);
        directions.add(direction);
        while(true) {
            if (newObstacle != null && inLoop(path, directions, extraTurnPoints)) {
                return null;
            }
            Point nextPos = curpos.translate(direction);
            if (!inbounds.test(nextPos)) return visited;
            if (map.get(nextPos.y).charAt(nextPos.x) != '#' && !blocked.test(nextPos)) {
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

    static void init() {
        width = map.get(0).length();
        height = map.size();
        inbounds = Point.boundsPredicate(width,height);
        for (int y=0; y< height; y++) {
            int x = map.get(y).indexOf('^');
            if (x >= 0) {
                start = new Point(x,y);
            }
            for (x = map.get(y).indexOf('#'); x >= 0; x = map.get(y).indexOf('#',x+1)) {
                Arrays.stream(new Point (x,y).neighbours()).filter(inbounds).forEach(turnpoints::add);
            }
        }
    }

    static void placeObstacles() {
        Set<Point> innerVisited = new LinkedHashSet<>(visited);
        innerVisited.remove(start);
        System.out.println(innerVisited.parallelStream().filter(o -> walk(o) == null).count());
    }
}
