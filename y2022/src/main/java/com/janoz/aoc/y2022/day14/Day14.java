package com.janoz.aoc.y2022.day14;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.StopWatch;
import com.janoz.aoc.geo.GrowingGrid;
import com.janoz.aoc.geo.Line;
import com.janoz.aoc.geo.Point;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day14 {

    static final Point HOLE = new Point(500,0);

    static GrowingGrid<Character> fall=new GrowingGrid<>('.');

    public static void main(String[] args) {
        String file = "inputs/2022/day14.txt";
        StopWatch.start();

        loadField(file);

        int sand = 0;
        while (dropSand()) {
            sand++;
        }
        System.out.println("Part 1:" + sand);

        int bottom = fall.getHeight() + 1;
        while (dropSand(bottom)) {
            sand++;
        }
        System.out.println("Part 2:" + sand);
        StopWatch.stopPrint();

        fall.printGrid(c -> c);

    }

    static boolean dropSand() {
        int curX = HOLE.x;
        int curY = HOLE.y;
        while (curY<fall.getHeight()-1) {
            if (fall.get(curX, curY+1) == '.') {
                curY++;
            } else if (fall.get(curX-1, curY+1) == '.') {
                curY++;
                curX--;
            } else if (fall.get(curX+1, curY+1) == '.') {
                curY++;
                curX++;
            } else {
                fall.put(new Point(curX,curY),'o');
                return true;
            }
        }
        return false;
    }

    static boolean dropSand(int bottom) {
        int curX = HOLE.x;
        int curY = HOLE.y;
        if (fall.get(curX,curY) == 'o') return false;
        while (true) {
            if (curY == bottom - 1) {
                fall.put(new Point(curX,curY),'o');
                return true;
            }
            if (fall.get(curX, curY+1) == '.') {
                curY++;
            } else if (fall.get(curX-1, curY+1) == '.') {
                curY++;
                curX--;
            } else if (fall.get(curX+1, curY+1) == '.') {
                curY++;
                curX++;
            } else {
                fall.put(new Point(curX,curY),'o');
                return true;
            }
        }
    }

    static void loadField(String file) {
        InputProcessor.asStream(file, s -> Arrays.stream(s.split("->"))
                .map(String::trim)
                .map(Point::parse)
                .collect(Collectors.toList())).forEach(points ->
                        IntStream.range(1,points.size()).forEach(i ->
                                Line.of(points.get(i-1), points.get(i)).drawOn(fall,'#')));
    }
}
