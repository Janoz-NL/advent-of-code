package com.janoz.aoc.y2024.day6;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.geo.BoundingBox;
import com.janoz.aoc.geo.Direction;
import com.janoz.aoc.geo.Grid;
import com.janoz.aoc.geo.Point;
import com.janoz.aoc.graphics.Graphics;

public class Day6 {

    static Set<Point> obstacles;
    static Predicate<Point> inbounds;
    static Set<Point> visited;
    static Point start;
    static Set<Point> turnpoints = new HashSet<>();

    static int width;
    static int height;

    public static void main(String[] args) {
        init("inputs/2024/day06.txt");
        visited = walk();
        System.out.println(visited.size());
        System.out.println(possibleLoops());

        List<BufferedImage> images = new ArrayList<>();
        images.add(Grid.asGrid(width, height, obstacles).toImage(x -> Color.RED));
        walk(null, p -> images.add(Grid.asGrid(width,height,Collections.singleton(p)).toImage(x -> Color.BLUE)));
        Graphics.writeAniGif(images, "target/AOC_2024_06.gif");
    }

    static Set<Point> walk() {
        return walk(null);
    }

    /**
     * @param obstacle extra obstacle or null for part 1
     * @return a set of visited nodes, or null if the guard is in a loop
     */
    static Set<Point> walk(Point obstacle) {
        return walk(obstacle, x -> {});
    }

    static Set<Point> walk(Point obstacle, Consumer<Point> pathStepConsumer) {
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
            pathStepConsumer.accept(curpos);
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
        BoundingBox bb = BoundingBox.readGrid(InputProcessor.asIterator(file), (p, c) -> {
            if (c == '^') start = p;
            if (c == '#') obstacles.add(p);
        });
        width = bb.getWidth();
        height = bb.getHeight();
        inbounds = bb.inBoundsPredicate();
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
