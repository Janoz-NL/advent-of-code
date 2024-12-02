package com.janoz.aoc.y2023.day8;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.Test;

public class Day8Test {

    @Test
    void testExample1a() {
        Day8 d = new Day8();
        d.read("inputs/2023/day08test.txt");
        assertThat(d.solve1()).isEqualTo(2);
    }

    @Test
    void testExample1b() {
        Day8 d = new Day8();
        d.read("inputs/2023/day08test2.txt");
        assertThat(d.solve1()).isEqualTo(6);
    }

    @Test
    void testExample2() {
        Day8 d = new Day8();
        d.read("inputs/2023/day08test3.txt");
        assertThat(d.solve2()).isEqualTo(6);
    }




}
