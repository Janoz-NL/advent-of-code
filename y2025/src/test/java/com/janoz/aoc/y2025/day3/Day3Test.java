package com.janoz.aoc.y2025.day3;

import com.janoz.aoc.input.AocInput;
import com.janoz.aoc.y2025.day2.Day2;
import org.junit.jupiter.api.Test;


class Day3Test {

    static final String TEST_INPUT =
            "987654321111111\n" +
            "811111111111119\n" +
            "234234234234278\n" +
            "818181911112111";

    @Test
    public void runTest() {
        Day3.solve(AocInput.ofString(TEST_INPUT));
    }
}