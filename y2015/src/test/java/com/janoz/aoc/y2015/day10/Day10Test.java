package com.janoz.aoc.y2015.day10;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day10Test {


    @Test
    void testnext() {
        assertThat(Day10.next("11")).isEqualTo("21");
        assertThat(Day10.next("21")).isEqualTo("1211");
        assertThat(Day10.next("111221")).isEqualTo("312211");
    }
}