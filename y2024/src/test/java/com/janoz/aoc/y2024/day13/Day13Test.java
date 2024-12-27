package com.janoz.aoc.y2024.day13;


import com.janoz.aoc.geo.Direction;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;

class Day13Test {

    @Test
    void testParseDirection() {
        assertThat(Day13.parseDirection("Button A: X+69, Y+23")).isEqualTo(new Direction(69,23));
    }

    @Test
    void testSize() {
        System.out.println(Integer.MAX_VALUE);
        System.out.println("10000000000000");
    }

    @Test
    void testParsePrize() {
        assertThat(Day13.parsePrize("Prize: X=18641, Y=10279")).isEqualTo(new Direction(18641,10279));
    }

    @Test
    void solvePart1_1() {
        Iterator<String> i =
            Arrays.asList(
                "Button A: X+94, Y+34",
                "Button B: X+22, Y+67",
                "Prize: X=8400, Y=5400").iterator();

        assertThat(Day13.solveClaw(Day13.parse(i),0)).isEqualTo(280);
    }

}