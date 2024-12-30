package com.janoz.aoc.y2015.day11;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day11Test {

    @Test
    void testValid() {
        assertThat(Day11.isValid("hijklmmn".toCharArray())).isFalse();
        assertThat(Day11.isValid("abcdffaa".toCharArray())).isTrue();
    }

    @Test
    void testNext() {
        assertThat(Day11.next("aaaaaaaa".toCharArray())).isEqualTo("aaaaaaab".toCharArray());
        assertThat(Day11.next("zzzzzzzz".toCharArray())).isEqualTo("aaaaaaaa".toCharArray());

    }
}