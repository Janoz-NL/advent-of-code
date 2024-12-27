package com.janoz.aoc.y2024.day8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.StopWatch;
import com.janoz.aoc.collections.AlwaysHashMap;
import com.janoz.aoc.geo.BoundingBox;
import com.janoz.aoc.geo.Direction;
import com.janoz.aoc.geo.Point;

public class Day8 {

    static Predicate<Point> boundingPredicate;
    static AlwaysHashMap<Character, List<Point>> antennas = new AlwaysHashMap<>(p-> new ArrayList<>());
    public static void main(String[] args) {
        StopWatch.start();
        boundingPredicate = BoundingBox.readGrid(
                InputProcessor.asIterator("inputs/2024/day08.txt"),
                (p,c) -> antennas.get(c).add(p)).inBoundsPredicate();
        System.out.println(calcAntiNodes());
        StopWatch.stopPrint();
    }

    static int calcAntiNodes() {
        return antennas.keySet().stream()
                .map(a -> getAntiNodes(antennas.get(a)))
                .flatMap(Set::stream)
                .filter(boundingPredicate)
                .collect(Collectors.toSet()).size();
    }

    static Set<Point> getAntiNodes(List<Point> antennas) {
        Set<Point> antiNodes= new HashSet<>();
        for (int i=0; i<antennas.size(); i++) {
            for (int j=i+1; j<antennas.size(); j++) {
                antiNodes.addAll(getAllAntiNodes(antennas.get(i), antennas.get(j)));
            }
        }
        return antiNodes;
    }

    static Collection<Point> getAntiNodes(Point a1, Point a2) {
        Direction d = new Direction(a1.x - a2.x, a1.y - a2.y);
        return Arrays.asList(a1.translate(d), a2.translate(d.reverse()));
    }

    static Collection<Point> getAllAntiNodes(Point a1, Point a2) {
        Set<Point> result = new HashSet<>();
        Direction d = a2.directionTo(a1);
        Point p = a1;
        while (boundingPredicate.test(p)) {
            result.add(p);
            p = p.translate(d);
        }
        p = a2;
        d=d.reverse();
        while (boundingPredicate.test(p)) {
            result.add(p);
            p = p.translate(d);
        }
        return result;
    }
}
