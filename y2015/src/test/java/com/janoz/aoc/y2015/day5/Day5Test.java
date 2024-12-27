package com.janoz.aoc.y2015.day5;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class Day5Test {


    @Test
    void testPart2() {
        Assertions.assertThat(Day5.validatePart2("qjhvhtzxzqqjkmpb")).isTrue();
        Assertions.assertThat(Day5.validatePart2("xxyxx")).isTrue();
        Assertions.assertThat(Day5.validatePart2("uurcxstgmygtbstg")).isFalse();
        Assertions.assertThat(Day5.validatePart2("ieodomkazucvgmuy")).isFalse();
        Assertions.assertThat(Day5.validatePart2("aaa")).isFalse();
        Assertions.assertThat(Day5.validatePart2("aaaa")).isTrue();


    }

}