package com.janoz.aoc.y2021.day7;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day7Test {

    Day7 cut;

    @BeforeEach
    void init() {
        cut = new Day7();
        cut.initPositions(new long[]{16,1,2,0,4,2,7,1,2,14});
    }

    @Test
    void testPart1() {
        assertThat(cut.part1()).isEqualTo(37L);
    }

    @Test
    void testPart2() {
        assertThat(cut.part2b()).isEqualTo(168L);
    }

    @Test
    void testCost2() {
        assertThat(cut.cost2(16,5)).isEqualTo(66L);
        assertThat(cut.cost2(1,5)).isEqualTo(10L);
        assertThat(cut.cost2(2,5)).isEqualTo(6L);
        assertThat(cut.cost2(0,5)).isEqualTo(15L);
        assertThat(cut.cost2(4,5)).isEqualTo(1L);
    }
}