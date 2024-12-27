package com.janoz.aoc.geo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PointTest {

    @Test
    void testParse() {
        Point p;
        p = Point.parse("0,9");
        assertThat(p.x).isEqualTo(0);
        assertThat(p.y).isEqualTo(9);

        p = Point.parse(" 123 , 456 ");
        assertThat(p.x).isEqualTo(123);
        assertThat(p.y).isEqualTo(456);
    }

    @Test
    void testDirections() {
        Point p = Point.parse("0,9");
        assertThat(p.east()).isEqualTo(new Point(1,9));
        assertThat(p.west()).isEqualTo(new Point(-1,9));
        assertThat(p.south()).isEqualTo(new Point(0,10));
        assertThat(p.north()).isEqualTo(new Point(0,8));
    }
}