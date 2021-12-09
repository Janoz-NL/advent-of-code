package com.janoz.aoc.geo;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LineTest {

    @Test
    void testParse() {
        Line l;
        l = Line.parse("8,9 -> 0,1");
        assertThat(l.start.x, equalTo(8));
        assertThat(l.start.y, equalTo(9));
        assertThat(l.end.x, equalTo(0));
        assertThat(l.end.y, equalTo(1));
    }

    @Test
    void testStraight() {
        assertThat(Line.parse("2,2 -> 2,1").isStraight(),is(true));
        assertThat(Line.parse("2,2 -> 1,2").isStraight(),is(true));
        assertThat(Line.parse("2,1 -> 1,2").isStraight(),is(false));
    }

    @Test
    void testDraw() {
        List<Point> points;
        points = Line.parse("1,2 -> 3,2").draw().collect(Collectors.toList());
        assertThat(points.size(),is(3));
        assertThat(points.contains(new Point(1,2)), is(true));
        assertThat(points.contains(new Point(2,2)), is(true));
        assertThat(points.contains(new Point(3,2)), is(true));

        points = Line.parse("3,2 -> 1,2").draw().collect(Collectors.toList());
        assertThat(points.size(),is(3));
        assertThat(points.contains(new Point(1,2)), is(true));
        assertThat(points.contains(new Point(2,2)), is(true));
        assertThat(points.contains(new Point(3,2)), is(true));

        points = Line.parse("2,1 -> 2,3").draw().collect(Collectors.toList());
        assertThat(points.size(),is(3));
        assertThat(points.contains(new Point(2,1)), is(true));
        assertThat(points.contains(new Point(2,2)), is(true));
        assertThat(points.contains(new Point(2,3)), is(true));

        points = Line.parse("2,3 -> 2,1").draw().collect(Collectors.toList());
        assertThat(points.size(),is(3));
        assertThat(points.contains(new Point(2,1)), is(true));
        assertThat(points.contains(new Point(2,2)), is(true));
        assertThat(points.contains(new Point(2,3)), is(true));

        points = Line.parse("1,2 -> 3,4").draw().collect(Collectors.toList());
        assertThat(points.size(),is(3));
        assertThat(points.contains(new Point(1,2)), is(true));
        assertThat(points.contains(new Point(2,3)), is(true));
        assertThat(points.contains(new Point(3,4)), is(true));

        points = Line.parse("3,4 -> 1,2").draw().collect(Collectors.toList());
        assertThat(points.size(),is(3));
        assertThat(points.contains(new Point(1,2)), is(true));
        assertThat(points.contains(new Point(2,3)), is(true));
        assertThat(points.contains(new Point(3,4)), is(true));

        points = Line.parse("3,2 -> 1,4").draw().collect(Collectors.toList());
        assertThat(points.size(),is(3));
        assertThat(points.contains(new Point(3,2)), is(true));
        assertThat(points.contains(new Point(2,3)), is(true));
        assertThat(points.contains(new Point(1,4)), is(true));

        points = Line.parse("1,4 -> 3,2").draw().collect(Collectors.toList());
        assertThat(points.size(),is(3));
        assertThat(points.contains(new Point(3,2)), is(true));
        assertThat(points.contains(new Point(2,3)), is(true));
        assertThat(points.contains(new Point(1,4)), is(true));


    }
}
