package com.janoz.aoc.y2024.day11;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day11Test {

    @Test
    void testParse() {
        Day11.parse("125 17");
        assertThat(Day11.stones.keySet()).contains(125L, 17L);
    }

    @Test
    void testBlink() {
        Day11.parse("125 17");
        Day11.blink(1);
        assertThat(Day11.stones.keySet()).contains(253000L, 1L, 7L);
        assertThat(Day11.count()).isEqualTo(3);
    }

    @Test
    void testCount() {
        Day11.parse("125 17");
        Day11.blink(25);
        assertThat(Day11.count()).isEqualTo(55312L);
    }
}