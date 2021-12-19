package com.janoz.aoc.y2021.day17;

import com.janoz.aoc.geo.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class Day17Test {

    @BeforeEach
    public void setTargetExample() {
        Day17.target = new Square(new Point(20,-5), new Point(30,-10));
    }

    @Test
    public void testSimulate() {
        assertThat(Day17.simulate(7,2), equalTo(true));
        assertThat(Day17.simulate(6,3), equalTo(true));
        assertThat(Day17.simulate(9,0), equalTo(true));
        assertThat(Day17.simulate(17,-4), equalTo(false));
    }

    @Test
    public void findTop() {
        assertThat(Day17.getTop(9), equalTo(45));
        assertThat(Day17.getTop(3), equalTo(6));
    }
}