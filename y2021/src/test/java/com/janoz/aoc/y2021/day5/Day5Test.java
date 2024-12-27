package com.janoz.aoc.y2021.day5;

import com.janoz.aoc.geo.Line;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class Day5Test {

    Day5 cut;

    @BeforeEach
    public void before() {
        cut = new Day5();
    }

    @Test
    void testStream() {
        List<Line> actual = cut.streamLines("inputs/day5example.txt").collect(Collectors.toList());
        assertThat(actual.size()).isEqualTo(10);
    }

    @Test
    void testPart1() {
        assertThat(cut.calculateScore1("inputs/day5example.txt")).isEqualTo(5L);
    }

    @Test
    void testPart2() {
        assertThat(cut.calculateScore2("inputs/day5example.txt")).isEqualTo(12L);
    }

}
