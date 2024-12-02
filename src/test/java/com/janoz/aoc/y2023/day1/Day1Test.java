package com.janoz.aoc.y2023.day1;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day1Test {

    @Test
    void testGetDigits() {
        assertThat(Day1.getDigits("treb7uchet")).isEqualTo(77);
        assertThat(Day1.getDigits("1treb7uchet2")).isEqualTo(12);
    }

    @Test
    void testReplace() {
        assertThat(Day1.replaceFirst("abcone2threexyz").toString()).isEqualTo("abc1ne2threexyz");
        assertThat(Day1.replaceLast("abcone2threexyz").toString()).isEqualTo("abcone23hreexyz");
    }

    @Test
    void testSample() {
        assertThat(Day1.solve("inputs/2023/day01test.txt")).isEqualTo(142);
        assertThat(Day1.solve2("inputs/2023/day01test2.txt")).isEqualTo(281);
    }
}
