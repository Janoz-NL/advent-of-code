package com.janoz.aoc.y2025.day1;

import com.janoz.aoc.input.AocInput;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

class Day1Test {

    static final String TEST_INPUT=
            """
                    L68
                    L30
                    R48
                    L5
                    R60
                    L55
                    L1
                    L99
                    R14
                    L82""";

    @Test
    public void runTest() {
        Day1.solve(AocInput.ofString(TEST_INPUT));
    }

    @Test
    public void runChallange1() throws FileNotFoundException {
        Day1.solve(AocInput.ofFile(new File("../challenges/aoc-2025-day-1-challenge-1.txt")));
    }
}