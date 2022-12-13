package com.janoz.aoc.geo;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BorderedGridTest {

    BorderedGrid cut;

    @Test
    void testBorder() {
        cut = BorderedGrid.from(4,4,-1);
        assertThat(cut.get(new Point(-1,0))).isEqualTo(-1);
        assertThat(cut.get(new Point(1,-1))).isEqualTo(-1);
        assertThat(cut.get(new Point(4,2))).isEqualTo(-1);
        assertThat(cut.get(new Point(2,4))).isEqualTo(-1);
    }

    @Test
    void testStreamPoints() {
        cut = BorderedGrid.from(4,4,-1);
        Point[] points = cut.streamPoints().toArray(Point[]::new);
        assertThat(points[0]).isEqualTo(new Point(0,0));
        assertThat(points[1]).isEqualTo(new Point(1,0));
        assertThat(points[2]).isEqualTo(new Point(2,0));
        assertThat(points[3]).isEqualTo(new Point(3,0));
        assertThat(points[4]).isEqualTo(new Point(0,1));
        assertThat(points[5]).isEqualTo(new Point(1,1));
        assertThat(points[6]).isEqualTo(new Point(2,1));
        assertThat(points[7]).isEqualTo(new Point(3,1));
        assertThat(points.length).isEqualTo(16);
    }

}