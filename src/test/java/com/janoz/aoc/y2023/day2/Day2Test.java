package com.janoz.aoc.y2023.day2;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day2Test {


    @Test
    void testParsSingleDraw() {
        assertThat(new Draw(" 3 blue, 4 red").toString()).isEqualTo("b3,g0,r4");

    }

    @Test
    void TestSolve() {
        assertThat(Day2.solve("inputs/2023/day02test.txt")).isEqualTo(8);
        assertThat(Day2.solve2("inputs/2023/day02test.txt")).isEqualTo(2286);

    }

}
