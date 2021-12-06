package com.janoz.aoc.y2021.day5;

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
}