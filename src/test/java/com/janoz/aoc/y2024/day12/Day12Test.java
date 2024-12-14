package com.janoz.aoc.y2024.day12;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.geo.GrowingGrid;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;

class Day12Test {


    @Test
    void testInit() {
        Iterator<String> input = toLines("AAAA\n" +
                "BBCD\n" +
                "BBCC\n" +
                "EEEC");
        Day12.map = GrowingGrid.readGrid(input);
        Day12.map.printGrid(c -> c);
    }


    @Test
    void testPart1() {
        Iterator<String> input = toLines("RRRRIICCFF\n" +
                "RRRRIICCCF\n" +
                "VVRRRCCFFF\n" +
                "VVRCCCJFFF\n" +
                "VVVVCJJCFE\n" +
                "VVIVCCJJEE\n" +
                "VVIIICJJEE\n" +
                "MIIIIIJJEE\n" +
                "MIIISIJEEE\n" +
                "MMMISSJEEE");
        Day12.map = GrowingGrid.readGrid(input);
        Day12.regions = Day12.map.connectedSets();
        assertThat(Day12.part1()).isEqualTo(1930L);
    }

    @Test
    void testPart2() {
        Iterator<String> input = toLines("AAAA\n" +
                "BBCD\n" +
                "BBCC\n" +
                "EEEC");
        Day12.map = GrowingGrid.readGrid(input);
        Day12.regions = Day12.map.connectedSets();
        assertThat(Day12.part2()).isEqualTo(80L);
    }

    @Test
    void testSolution() {
        Day12.map = GrowingGrid.readGrid(InputProcessor.asIterator("inputs/2024/day12.txt"));
        Day12.regions = Day12.map.connectedSets();
        assertThat(Day12.part1()).isEqualTo(1421958L);
        assertThat(Day12.part2()).isEqualTo(885394L);
    }

    private Iterator<String> toLines(String input) {
        return Arrays.stream(input.trim().split("\n")).iterator();
    }
}