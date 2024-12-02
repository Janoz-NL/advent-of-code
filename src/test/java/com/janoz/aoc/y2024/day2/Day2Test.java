package com.janoz.aoc.y2024.day2;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day2Test {


    @Test
    void testIncreasing() {
        assertThat(Day2.isSaveIncreasing(new int[]{1,2,4})).isEqualTo(1);
    }
}
