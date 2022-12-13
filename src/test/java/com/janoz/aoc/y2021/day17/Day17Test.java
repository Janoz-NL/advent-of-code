package com.janoz.aoc.y2021.day17;

import com.janoz.aoc.geo.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day17Test {

    @BeforeEach
    public void setTargetExample() {
        Day17.target = new Square(new Point(20,-5), new Point(30,-10));
    }

    @Test
    public void testSimulate() {
        assertThat(Day17.simulate(7,2)).isTrue();
        assertThat(Day17.simulate(6,3)).isTrue();
        assertThat(Day17.simulate(9,0)).isTrue();
        assertThat(Day17.simulate(17,-4)).isFalse();
    }

    @Test
    public void findTop() {
        assertThat(Day17.getTop(9)).isEqualTo(45);
        assertThat(Day17.getTop(3)).isEqualTo(6);
    }
}