package com.janoz.aoc.y2024.day6;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.geo.Point;

public class Day6 {

    static List<String> map;
    static int width;
    static int height;
    static Predicate<Point> inbounds;
    static Set<Point> visited;
    public static void main(String[] args) {
        map = InputProcessor.asStream("inputs/2024/day06.txt").collect(Collectors.toList());
        width = map.get(0).length();
        height = map.size();
        inbounds = Point.boundsPredicate(width,height);
        visited = walk();
        System.out.println(visited.size());
        placeObstacles();
    }

    static Set<Point> walk() {
        return walk(p->false);
    }

    static Set<Point> walk(Predicate<Point> blocked) {
        Set<Point> visited = new LinkedHashSet<>();
        Point curpos = findStart();
        Point direction = new Point(0,-1);
        visited.add(curpos);
        List<Point> path = new ArrayList<>();
        List<Point> directions = new ArrayList<>();
        path.add(curpos);
        directions.add(direction);
        while(true) {
            if (inLoop(path, directions)) {
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
                direction = rotate(direction);
                directions.set(directions.size()-1, direction);
            }
        }
    }

    /**
     * @return true when last point added created a loop
     */
    static boolean inLoop(List<Point> path, List<Point> direction) {
        Point pos = path.get(path.size()-1);
        Point dir = direction.get(path.size()-1);
        for (int i=0; i<path.size()-2; i++) {
            if (path.get(i).equals(pos) && direction.get(i).equals(dir)) return true;
        }
        return false;
    }

    static Point findStart() {
        for (int y=0; y< height; y++) {
            int x = map.get(y).indexOf('^');
            if (x >= 0) {
                return new Point(x,y);
            }
        }
        throw new RuntimeException("Guard not found");
    }

    static void placeObstacles() {
        Set<Point> innerVisited = new LinkedHashSet<>(visited);
        innerVisited.remove(findStart());
        System.out.println(innerVisited.stream().filter(o -> walk(o::equals) == null ).count());
    }

    static Point rotate(Point p) {
        return new Point(p.y * -1, p.x);
    }
}
