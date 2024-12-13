package com.janoz.aoc.y2024.day12;

import com.janoz.aoc.graphics.Graphics;
import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.StopWatch;
import com.janoz.aoc.geo.GrowingGrid;
import com.janoz.aoc.geo.Point;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class Day12 {

    static GrowingGrid<Character> map;
    static Map<Integer, Set<Point>> regions;

    public static void main(String[] args) {
        StopWatch.start();
        Iterator<String> input = InputProcessor.asIterator("inputs/2024/day12.txt");

        map = GrowingGrid.readGrid(input);
        regions = map.connectedSets();
        System.out.println(Day12.part1());
        System.out.println(Day12.part2());
        StopWatch.stopPrint();

        // And now for the graphics
        Function<Integer, Color> colorMapper = Graphics.constructMapper(16);
        List<BufferedImage> images = new ArrayList<>();
        map.connectedSets(frame -> {
            BufferedImage image = frame.toImage(colorMapper);
            if (image != null) {
                images.add(image);
            }
        });
        Graphics.writeAniGif(images, "target/AOC_2024_12.gif");
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
            Point[] adjacent = p.adjacent();
            for (int i = 0; i < 8; i += 2) {
                if (points.contains(adjacent[(i + 7) & 7]) &&
                        points.contains(adjacent[(i + 1) & 7]) &&
                        !points.contains(adjacent[i & 7])) {
                    //inner corner
                    posts++;
                } else if (!points.contains(adjacent[(i + 7) & 7]) &&
                        !points.contains(adjacent[(i + 1) & 7])) {
                    //outer corner
                    posts++;
                }
            }
            return posts;
        }).sum();
    }
}