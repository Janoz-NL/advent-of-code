package com.janoz.aoc.y2021.day12;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class Day12Test {

    Day12 cut;


    @BeforeEach
    public void before() {
        cut = new Day12();
    }


    @Test
    public void testPart1Example1() {
        cut.read("inputs/day12example1.txt");
        assertThat(cut.travel(), equalTo(10L));
    }

    @Test
    public void testPart2Example1() {
        cut.read("inputs/day12example1.txt");
        assertThat(cut.travelLonger(), equalTo(36L));
    }

}