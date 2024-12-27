package com.janoz.aoc.y2021.day11;

import com.janoz.aoc.geo.BorderedGrid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day11Test {

    Day11 cut;


    @BeforeEach
    public void before() {
        cut = new Day11();
    }



    @Test
    public void testExampleGrids() {
        cut.load("inputs/day11example.txt");
        cut.step();
        assertThat(cut.grid).isEqualTo(BorderedGrid.fromSingleDigitFile("inputs/day11example_step1.txt",-1));
        cut.step();
        assertThat(cut.grid).isEqualTo(BorderedGrid.fromSingleDigitFile("inputs/day11example_step2.txt",-1));
        cut.step();
        assertThat(cut.grid).isEqualTo(BorderedGrid.fromSingleDigitFile("inputs/day11example_step3.txt",-1));
    }

    @Test
    public void testExample() {
        cut.load("inputs/day11example.txt");
        assertThat(cut.part1()).isEqualTo(1656L);
    }

    @Test
    public void testPart2() {
        cut.load("inputs/day11example.txt");
        assertThat(cut.part2()).isEqualTo(195L);
    }

}
