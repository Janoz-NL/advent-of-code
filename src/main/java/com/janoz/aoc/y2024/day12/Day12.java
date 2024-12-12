package com.janoz.aoc.y2024.day12;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.collections.AlwaysHashMap;
import com.janoz.aoc.collections.MergingMap;
import com.janoz.aoc.geo.BoundingBox;
import com.janoz.aoc.geo.GrowingGrid;
import com.janoz.aoc.geo.Point;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class Day12 {


    static GrowingGrid<Character> map;
    static GrowingGrid<Integer> regionGrid;
    static Predicate<Point> inBounds;
    static MergingMap regionMapper;
    static Map<Integer, Set<Point>> regions;


    public static void main(String[] args) {
        init(InputProcessor.asIterator("inputs/2024/day12.txt"));
        Day12.floodfill();
        System.out.println(Day12.part1());
        System.out.println(Day12.part2());
    }


    static void init(Iterator<String> input) {
        map = new GrowingGrid<>('.');
        regionGrid = new GrowingGrid<>(0);
        inBounds = BoundingBox
                .readGrid(input, (p, c) -> map.put(p,  c))
                .inBoundsPredicate();
        regionMapper = new MergingMap();
        regions = new AlwaysHashMap<>((Supplier<Set<Point>>)HashSet::new);
    }

    static void floodfill() {
        Iterator<Integer> nextRegionSupplier = IntStream.iterate(1,i->i+1).iterator();
        Stack<Point> stack = new Stack<>();
        stack.push(Point.ORIGIN);
        regionGrid.put(Point.ORIGIN,nextRegionSupplier.next());
        while (!stack.isEmpty()) {
            Point p = stack.pop();
            char c = map.get(p);
            int region = regionGrid.get(p);
            p.streamNeighbour(inBounds).forEach(n-> {
                if (map.get(n) == c) {
                    int otherRegion = regionGrid.get(n);
                    if (otherRegion == 0) {
                        regionGrid.put(n,region);
                        stack.push(n);
                    } else if (otherRegion != region) {
                        regionMapper.addMapping(region, otherRegion);
                    }
                } else {
                    if (regionGrid.get(n) == 0) {
                        regionGrid.put(n,nextRegionSupplier.next());
                        stack.push(n);
                    }
                }
            });
        }
        regionGrid.streamPoints().forEach(p-> regions.get(regionMapper.getActual(regionGrid.get(p))).add(p));
    }

    static long part1() {
        return regions.values().stream().mapToLong(points -> area(points) * perimeter(points)).sum();
    }

    static long part2() {
        return regions.values().stream().mapToLong(points -> area(points) * bulkPerimiter(points)).sum();
    }

    static long area(Set<Point> points) {
        return points.size();
    }

    static long perimeter(Set<Point> points) {
        return points.stream()
                .mapToLong(p -> p.streamNeighbour(n -> !points.contains(n)).count()).sum();
    }

    static long bulkPerimiter(Collection<Point> points) {
        return points.stream().mapToLong(p -> {
            int posts = 0;
            //top left
            if (points.contains(p.north()) == points.contains(p.west())) {
                if (points.contains(p.north())) {
                    if (!points.contains(p.north().west())) {
                        posts++;
                    }
                } else {
                    posts++;
                }
            }
            //top right
            if (points.contains(p.north()) == points.contains(p.east())) {
                if (points.contains(p.north())) {
                    if (!points.contains(p.north().east())) {
                        posts++;
                    }
                } else {
                    posts++;
                }
            }
            //bottom left
            if (points.contains(p.south()) == points.contains(p.west())) {
                if (points.contains(p.south())) {
                    if (!points.contains(p.south().west())) {
                        posts++;
                    }
                } else {
                    posts++;
                }
            }
            //bottom right
            if (points.contains(p.south()) == points.contains(p.east())) {
                if (points.contains(p.south())) {
                    if (!points.contains(p.south().east())) {
                        posts++;
                    }
                } else {
                    posts++;
                }
            }
            return posts;
        }).sum();
    }
}
