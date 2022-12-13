package com.janoz.aoc.geo;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class LineTest {

    @Test
    void testParse() {
        Line l;
        l = Line.parse("8,9 -> 0,1");
        assertThat(l.start.x).isEqualTo(8);
        assertThat(l.start.y).isEqualTo(9);
        assertThat(l.end.x).isEqualTo(0);
        assertThat(l.end.y).isEqualTo(1);
    }

    @Test
    void testStraight() {
        assertThat(Line.parse("2,2 -> 2,1").isStraight()).isTrue();
        assertThat(Line.parse("2,2 -> 1,2").isStraight()).isTrue();
        assertThat(Line.parse("2,1 -> 1,2").isStraight()).isFalse();
    }

    @Test
    void testDraw() {
        List<Point> points;
        points = Line.parse("1,2 -> 3,2").draw().collect(Collectors.toList());
        assertThat(points.size()).isEqualTo(3);
        assertThat(points.contains(new Point(1,2))).isTrue();
        assertThat(points.contains(new Point(2,2))).isTrue();
        assertThat(points.contains(new Point(3,2))).isTrue();

        points = Line.parse("3,2 -> 1,2").draw().collect(Collectors.toList());
        assertThat(points.size()).isEqualTo(3);
        assertThat(points.contains(new Point(1,2))).isTrue();
        assertThat(points.contains(new Point(2,2))).isTrue();
        assertThat(points.contains(new Point(3,2))).isTrue();

        points = Line.parse("2,1 -> 2,3").draw().collect(Collectors.toList());
        assertThat(points.size()).isEqualTo(3);
        assertThat(points.contains(new Point(2,1))).isTrue();
        assertThat(points.contains(new Point(2,2))).isTrue();
        assertThat(points.contains(new Point(2,3))).isTrue();

        points = Line.parse("2,3 -> 2,1").draw().collect(Collectors.toList());
        assertThat(points.size()).isEqualTo(3);
        assertThat(points.contains(new Point(2,1))).isTrue();
        assertThat(points.contains(new Point(2,2))).isTrue();
        assertThat(points.contains(new Point(2,3))).isTrue();

        points = Line.parse("1,2 -> 3,4").draw().collect(Collectors.toList());
        assertThat(points.size()).isEqualTo(3);
        assertThat(points.contains(new Point(1,2))).isTrue();
        assertThat(points.contains(new Point(2,3))).isTrue();
        assertThat(points.contains(new Point(3,4))).isTrue();

        points = Line.parse("3,4 -> 1,2").draw().collect(Collectors.toList());
        assertThat(points.size()).isEqualTo(3);
        assertThat(points.contains(new Point(1,2))).isTrue();
        assertThat(points.contains(new Point(2,3))).isTrue();
        assertThat(points.contains(new Point(3,4))).isTrue();

        points = Line.parse("3,2 -> 1,4").draw().collect(Collectors.toList());
        assertThat(points.size()).isEqualTo(3);
        assertThat(points.contains(new Point(3,2))).isTrue();
        assertThat(points.contains(new Point(2,3))).isTrue();
        assertThat(points.contains(new Point(1,4))).isTrue();

        points = Line.parse("1,4 -> 3,2").draw().collect(Collectors.toList());
        assertThat(points.size()).isEqualTo(3);
        assertThat(points.contains(new Point(3,2))).isTrue();
        assertThat(points.contains(new Point(2,3))).isTrue();
        assertThat(points.contains(new Point(1,4))).isTrue();
    }
}
