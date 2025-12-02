package com.janoz.aoc.y2025.day2;

import com.janoz.aoc.StopWatch;
import com.janoz.aoc.input.AocInput;
import com.janoz.aoc.y2025.day1.Day1;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;


class Day2Test {

    static final String TEST_INPUT = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224," +
            "1698522-1698528,446443-446449,38593856-38593862,565653-565659," +
            "824824821-824824827,2121212118-2121212124";

    @Test
    public void runTest() {
        Day2.solve(AocInput.ofString(TEST_INPUT));
    }

    @Test
    public void runChallange1() throws FileNotFoundException {
        StopWatch.start();
        long part1 = Day2.solveParalel1(AocInput.ofFile(new File("../challenges/aoc-2025-day-2-challenge-1.txt")));
        StopWatch.stopPrint();
        StopWatch.start();
        long part2 = Day2.solveParalel2(AocInput.ofFile(new File("../challenges/aoc-2025-day-2-challenge-1.txt")));
        StopWatch.stopPrint();
        System.out.printf("%d\t%d%n", part1, part2);
    }

}