package com.janoz.aoc.y2015.day9;

import com.janoz.aoc.input.AocInput;
import org.junit.jupiter.api.Test;

class Day9Test {

    @Test
    void testExample() {
        Day9.solve(AocInput.ofString("""
                London to Dublin = 464
                London to Belfast = 518
                Dublin to Belfast = 141
                """));
    }
}