package com.janoz.aoc.y2015.day7;

import com.janoz.aoc.input.AocInput;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day7Test {


    @Test
    void testExample1() {
        Day7.solve(AocInput.ofString("""
                123 -> x
                456 -> y
                x AND y -> d
                x OR y -> e
                x LSHIFT 2 -> f
                y RSHIFT 2 -> g
                NOT x -> h
                NOT y -> i
                """));
        assertThat(Day7.getResult("d")).isEqualTo(72L);
        assertThat(Day7.getResult("e")).isEqualTo(507L);
        assertThat(Day7.getResult("f")).isEqualTo(492L);
        assertThat(Day7.getResult("g")).isEqualTo(114L);
        assertThat(Day7.getResult("h")).isEqualTo(65412L);
        assertThat(Day7.getResult("i")).isEqualTo(65079L);
        assertThat(Day7.getResult("x")).isEqualTo(123L);
        assertThat(Day7.getResult("y")).isEqualTo(456L);
    }

}