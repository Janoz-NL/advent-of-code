package com.janoz.aoc.y2022.day3;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day3Test {

    @Test
    void testConversion() {
        assertThat(Day3.toPriority('a')).isEqualTo(1);
        assertThat(Day3.toPriority('A')).isEqualTo(27);
    }

}