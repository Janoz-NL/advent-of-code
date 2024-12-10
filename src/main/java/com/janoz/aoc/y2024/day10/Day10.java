package com.janoz.aoc.y2024.day10;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.geo.BoundingBox;
import com.janoz.aoc.geo.GrowingGrid;
import com.janoz.aoc.geo.Point;

public class Day10 {

    static Set<Point> trailHeads;
    static GrowingGrid<Integer> map;
    private static Predicate<Point> inBounds;

    public static void main(String[] args) {
        init(InputProcessor.asIterator("inputs/2024/day10.txt"));
        System.out.println(part1());
        System.out.println(climbingTrails());
    }

    static void init(Iterator<String> input) {
        map = new GrowingGrid<>(-1);
        trailHeads = new LinkedHashSet<>();
        inBounds = BoundingBox.readGrid(input, (p, c) -> {
            map.put(p,  c - '0');
            if (c=='0') trailHeads.add(p);
        }).inBoundsPredicate();
    }

    static long part1() {
        return trailHeads.stream().map(Day10::reachableTops).mapToLong(Set::size).sum();
    }

    static Set<Point> reachableTops(Point start) {
        final int nextHeight = map.get(start) + 1;
        if (nextHeight == 10) {
            HashSet<Point> result = new HashSet<>();
            result.add(start);
            return result;
        }
        return start.streamNeighbour(inBounds)
                .filter(p -> map.get(p) == nextHeight)
                .map(Day10::reachableTops)
                .collect(HashSet::new,
                        Set::addAll,
                        Set::addAll
                        );
    }

    static long climbingTrails() {
        return trailHeads.stream().mapToLong(Day10::climbingTrail).sum();
    }

    static int climbingTrail(Point start) {
        final int nextHeight = map.get(start) + 1;
        if (nextHeight == 10) {
            return 1;
        }
        return start.streamNeighbour(inBounds)
                .filter(p -> map.get(p) == nextHeight)
                .mapToInt(Day10::climbingTrail)
                .sum();
    }
}


