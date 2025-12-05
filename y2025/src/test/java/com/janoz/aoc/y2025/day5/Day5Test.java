package com.janoz.aoc.y2025.day5;

import com.janoz.aoc.input.AocInput;
import org.junit.jupiter.api.Test;

class Day5Test {

    static String INPUT = """
            3-5
            10-14
            16-20
            12-18
            
            1
            5
            8
            11
            17
            32
            """;

    @Test
    void test() {
        Day5.solve(AocInput.ofString(INPUT));
    }
}