package com.janoz.aoc.y2025.day10;

import com.janoz.aoc.input.AocInput;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day10Test {

    static final String INPUT = "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}\n" +
            "[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}\n" +
            "[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}";

    @Test
    void test() {
        Day10.printResult(Day10.solve(AocInput.ofString(INPUT)));
    }

    @Test
    void parseButton() {
        assertThat(Day10.parseButton("(0,2,3,4)")).isEqualTo(0b11101);
    }

    @Test
    void parseIndicators() {
        assertThat(Day10.parseIndicator("[.###.#]")).isEqualTo(0b101110);
    }
}