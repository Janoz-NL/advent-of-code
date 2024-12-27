package com.janoz.aoc.y2023.day4;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day4Test {

    @Test
    void testExample1() {
        assertThat(Day4.solve1("inputs/2023/day04test.txt")).isEqualTo(13);
    }

    @Test
    void testExample2() {
        assertThat(Day4.solve2("inputs/2023/day04test.txt")).isEqualTo(30);
    }

    @Test
    void Parse() {
        String s = "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53";
        assertThat(Day4.match(s)).isEqualTo(4);
    }
}
