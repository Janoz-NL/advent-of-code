package com.janoz.aoc.y2015.day3;

import com.janoz.aoc.input.AocInput;
import org.junit.jupiter.api.Test;

class Day3Test {

    @Test
    void testExample1() {
        Day3.solve(AocInput.ofString("^v"));
    }

    @Test
    void testExample2() {
        Day3.solve(AocInput.ofString("^>v<"));
    }
}