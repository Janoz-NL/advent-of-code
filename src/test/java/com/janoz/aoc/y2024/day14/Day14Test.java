package com.janoz.aoc.y2024.day14;

import com.janoz.aoc.geo.Direction;
import com.janoz.aoc.geo.Point;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day14Test {

    @Test
    void testParseRobot() {
        assertThat(new Day14.Robot("p=0,4 v=3,-3"))
                .hasFieldOrPropertyWithValue("speed", new Direction(3,-3))
                .hasFieldOrPropertyWithValue("position", new Point(0, 4) );
    }

    @Test
    void testBox() {
        assertThat(Day14.box(-14,5)).isEqualTo(1);
    }
}