package com.janoz.aoc.y2024.day8;

import static org.assertj.core.api.Assertions.assertThat;

import com.janoz.aoc.geo.Point;

import org.junit.jupiter.api.Test;

public class Day8Test {


    @Test
    void testAntiNodes() {
        assertThat(Day8.getAntiNodes(new Point(6,6), new Point(8,9))).hasSize(2).contains(
                new Point(4,3),
                new Point(10,12)
        );
    }
}
