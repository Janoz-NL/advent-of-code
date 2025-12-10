package com.janoz.aoc.y2025.day9;

import com.janoz.aoc.input.AocInput;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day9Test {

    static final String INPUT = """
            7,1
            11,1
            11,7
            9,7
            9,5
            2,5
            2,3
            7,3""";

    @Test
    void test() {
        Day9.printResult(Day9.solve(AocInput.ofString(INPUT)));
    }

    @Test
    void testSoultaker() {
       assertThat(Day9.solve(AocInput.ofString("1,1\n" +
               "9,1\n" +
               "9,9\n" +
               "7,9\n" +
               "7,3\n" +
               "3,3\n" +
               "3,9\n" +
               "1,9")).getRight()).isEqualTo(27);
    }
}