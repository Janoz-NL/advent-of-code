package com.janoz.aoc.y2024.day12;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;

class Day12Test {


    @Test
    void testInit() {
        Day12.init(toLines("AAAA\n" +
                "BBCD\n" +
                "BBCC\n" +
                "EEEC"));
        Day12.map.printGrid(c -> c);
        Day12.floodfill();
        Day12.regionGrid.printGrid(i -> (char)('a' -1 + Day12.regionMapper.getActual(i)));
    }


    @Test
    void testRegions() {
        Day12.init(toLines("RRRRIICCFF\n" +
                "RRRRIICCCF\n" +
                "VVRRRCCFFF\n" +
                "VVRCCCJFFF\n" +
                "VVVVCJJCFE\n" +
                "VVIVCCJJEE\n" +
                "VVIIICJJEE\n" +
                "MIIIIIJJEE\n" +
                "MIIISIJEEE\n" +
                "MMMISSJEEE"));
        Day12.floodfill();
        System.out.println(Day12.part1());
    }

    @Test
    void testPart2() {
        Day12.init(toLines("AAAA\n" +
                "BBCD\n" +
                "BBCC\n" +
                "EEEC"));
        Day12.floodfill();
        System.out.println(Day12.part2());
    }


    private Iterator<String> toLines(String input) {
        return Arrays.stream(input.trim().split("\n")).iterator();
    }
}