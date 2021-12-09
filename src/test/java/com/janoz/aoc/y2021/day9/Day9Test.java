package com.janoz.aoc.y2021.day9;

import com.janoz.aoc.geo.Point;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


class Day9Test {


    @Test
    void testValeys() {
        Day9 d = Day9.fromFile("inputs/day9example.txt");
        Set<Point> valeys= d.findValeys();
        assertThat(valeys.contains(new Point(0,0)), equalTo(false));
        assertThat(valeys.contains(new Point(1,0)), equalTo(true));
        assertThat(valeys.contains(new Point(9,0)), equalTo(true));
        assertThat(valeys.contains(new Point(2,2)), equalTo(true));
        assertThat(valeys.contains(new Point(6,4)), equalTo(true));
    }

    @Test
    void testInputPart1() {
        Day9 d = Day9.fromFile("inputs/day9example.txt");
        assertThat(d.scorePart1(d.findValeys()), equalTo(15));
    }

    @Test
    void testInputPart2() {
        Day9 d = Day9.fromFile("inputs/day9example.txt");
        d.createBasinIds();
        assertThat(d.scorePart2(), equalTo(1134));
    }
}