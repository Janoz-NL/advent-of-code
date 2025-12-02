package com.janoz.aoc.y2024.day10;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.janoz.aoc.geo.GrowingGrid;
import com.janoz.aoc.geo.Point;
import com.janoz.aoc.input.AocInput;

public class Day10 {

    static Set<Point> trailHeads;
    static GrowingGrid<Integer> map;
    private static Predicate<Point> inBounds;

    public static void main(String[] args) {
        init(AocInput.of(2024,10).iterator());
        System.out.println(part1());
        System.out.println(part2());
    }

    static void init(Iterator<String> input) {
        map = GrowingGrid.readGrid(input, c -> c - '0', -1);
        trailHeads = map.streamPoints().filter(p -> map.peek(p) == 0).collect(Collectors.toSet());
        inBounds = map.inBoundsPredicate();
    }

    static long part1() {
        return trailHeads.stream().map(Day10::reachableTops).mapToLong(Set::size).sum();
    }

    static Set<Point> reachableTops(Point start) {
        final int nextHeight = map.get(start) + 1;
        if (nextHeight == 10) {
            return Collections.singleton(start);
        }
        return start.streamNeighbour(inBounds)
                .filter(p -> map.get(p) == nextHeight)
                .map(Day10::reachableTops)
                .collect(HashSet::new,
                        Set::addAll,
                        Set::addAll
                        );
    }

    static long part2() {
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


