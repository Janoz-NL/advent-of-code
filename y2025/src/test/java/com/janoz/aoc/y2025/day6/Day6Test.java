package com.janoz.aoc.y2025.day6;

import com.janoz.aoc.input.AocInput;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day6Test {

    static String INPUT =
            "123 328  51 64 \n" +
            " 45 64  387 23 \n" +
            "  6 98  215 314\n" +
            "*   +   *   +  ";

    @Test
    void test1() {
        System.out.println(Day6.solve1(AocInput.ofString(INPUT)));
    }

    @Test
    void test2() {
        System.out.println(Day6.solve2(AocInput.ofString(INPUT,s->s)));
    }
}