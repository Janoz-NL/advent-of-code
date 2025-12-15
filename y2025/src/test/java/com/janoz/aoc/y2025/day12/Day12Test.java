package com.janoz.aoc.y2025.day12;

import com.janoz.aoc.StopWatch;
import com.janoz.aoc.input.AocInput;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day12Test {

    static final String INPUT3 = "0:\n" +
            "###\n" +
            "##.\n" +
            "##.\n" +
            "\n" +
            "1:\n" +
            "###\n" +
            "##.\n" +
            ".##\n" +
            "\n" +
            "2:\n" +
            ".##\n" +
            "###\n" +
            "##.\n" +
            "\n" +
            "3:\n" +
            "##.\n" +
            "###\n" +
            "##.\n" +
            "\n" +
            "4:\n" +
            "###\n" +
            "#..\n" +
            "###\n" +
            "\n" +
            "5:\n" +
            "###\n" +
            ".#.\n" +
            "###\n" +
            "\n" +
            "4x4: 0 0 0 0 2 0\n" +
            "12x5: 1 0 1 0 2 2\n" +
            "12x5: 1 0 1 0 3 2";

    @Test
    void test() {
        StopWatch.start();
        Day12.printResult(Day12.solve(AocInput.ofString(INPUT3)));
        StopWatch.stopPrint();
    }
}