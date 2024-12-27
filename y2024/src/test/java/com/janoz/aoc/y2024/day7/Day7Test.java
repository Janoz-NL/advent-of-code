package com.janoz.aoc.y2024.day7;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class Day7Test {

    @Test
    void testPart1() {
//        assertThat(Day7.trySolve(190, Arrays.asList(10L,19L))).isTrue();
        assertThat(Day7.trySolve(3267, Arrays.asList(81L,40L,27L))).isTrue();
        assertThat(Day7.trySolve(292, Arrays.asList(11L,6L,16L,20L))).isTrue();
    }
}
