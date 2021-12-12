package com.janoz.aoc.y2021.day11;

import com.janoz.aoc.geo.BorderedGrid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

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
        assertThat(cut.grid, equalTo(BorderedGrid.fromSingleDigitFile("inputs/day11example_step1.txt",-1)));
        cut.step();
        assertThat(cut.grid, equalTo(BorderedGrid.fromSingleDigitFile("inputs/day11example_step2.txt",-1)));
        cut.step();
        assertThat(cut.grid, equalTo(BorderedGrid.fromSingleDigitFile("inputs/day11example_step3.txt",-1)));
    }

    @Test
    public void testExample() {
        cut.load("inputs/day11example.txt");
        assertThat(cut.part1(), equalTo(1656L));
    }

    @Test
    public void testPart2() {
        cut.load("inputs/day11example.txt");
        assertThat(cut.part2(), equalTo(195L));
    }

}
