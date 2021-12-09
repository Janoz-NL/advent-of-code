package com.janoz.aoc.geo;


import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class BorderedGridTest {


    BorderedGrid cut;


    @Test
    void testBorder() {
        cut = BorderedGrid.from(4,4,-1);
        assertThat(cut.get(new Point(-1,0)), is(-1));
        assertThat(cut.get(new Point(1,-1)), is(-1));
        assertThat(cut.get(new Point(4,2)), is(-1));
        assertThat(cut.get(new Point(2,4)), is(-1));
    }

    @Test
    void testStreamPoints() {
        cut = BorderedGrid.from(4,4,-1);
        Point[] points = cut.streamPoints().toArray(Point[]::new);
        assertThat(points[0], is(new Point(0,0)));
        assertThat(points[1], is(new Point(1,0)));
        assertThat(points[2], is(new Point(2,0)));
        assertThat(points[3], is(new Point(3,0)));
        assertThat(points[4], is(new Point(0,1)));
        assertThat(points[5], is(new Point(1,1)));
        assertThat(points[6], is(new Point(2,1)));
        assertThat(points[7], is(new Point(3,1)));
        assertThat(points.length, is(16));
    }

}