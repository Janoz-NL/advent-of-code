package com.janoz.aoc.y2015.day8;

import com.janoz.aoc.input.AocInput;
import org.junit.jupiter.api.Test;

class Day8Test {

    @Test
    void testInput() {
        Day8.solve(AocInput.ofString("""
                ""
                "abc"
                "aaa\\"aaa"
                "\\x27"\s
                """));
    }
}