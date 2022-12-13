package com.janoz.aoc.y2021.day12;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day12Test {

    Day12 cut;


    @BeforeEach
    public void before() {
        cut = new Day12();
    }


    @Test
    public void testPart1Example1() {
        cut.read("inputs/day12example1.txt");
        assertThat(cut.travel()).isEqualTo(10L);
    }

    @Test
    public void testPart2Example1() {
        cut.read("inputs/day12example1.txt");
        assertThat(cut.travelLonger()).isEqualTo(36L);
    }

}