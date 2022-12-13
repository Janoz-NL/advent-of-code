package com.janoz.aoc.y2021.day13;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day13Test {



    @Test
    public void testFoldY() {
        Day13 d = new Day13();
        d.part1("inputs/day13example.txt");
        assertThat(d.count()).isEqualTo(17L);
    }
}