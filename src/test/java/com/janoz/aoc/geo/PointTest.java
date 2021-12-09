package com.janoz.aoc.geo;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


class PointTest {

    @Test
    void testParse() {
        Point p;
        p = Point.parse("0,9");
        assertThat(p.x, equalTo(0));
        assertThat(p.y, equalTo(9));

        p = Point.parse(" 123 , 456 ");
        assertThat(p.x, equalTo(123));
        assertThat(p.y, equalTo(456));
    }

    @Test
    void testDirections() {
        Point p = Point.parse("0,9");
        assertThat(p.east(), equalTo(new Point(1,9)));
        assertThat(p.west(), equalTo(new Point(-1,9)));
        assertThat(p.south(), equalTo(new Point(0,10)));
        assertThat(p.north(), equalTo(new Point(0,8)));


    }

}