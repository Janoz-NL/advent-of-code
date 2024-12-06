package com.janoz.aoc.y2024.day6;

import static org.assertj.core.api.Assertions.assertThat;

import com.janoz.aoc.geo.Point;

import org.junit.jupiter.api.Test;

public class Day6Test {

    @Test
    void testRotate() {
        assertThat(Day6.rotate(new Point(0,-1))).isEqualTo(new Point(1,0));
        assertThat(Day6.rotate(new Point(1,0))).isEqualTo(new Point(0,1));
        assertThat(Day6.rotate(new Point(0,1))).isEqualTo(new Point(-1,0));
        assertThat(Day6.rotate(new Point(-1,0))).isEqualTo(new Point(0,-1));
    }
}
