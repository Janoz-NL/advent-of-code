package com.janoz.aoc.geo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GrowingGridTest {

    GrowingGrid<Integer> cut;

    @BeforeEach
    public void before() {
        cut = new GrowingGrid<>(0);
    }

    @Test
    public void testResize() {
        assertThat(cut.getWidth()).isEqualTo(0);
        assertThat(cut.getHeight()).isEqualTo(0);
        assertThat(cut.get(new Point(2,4))).isEqualTo(0);
        assertThat(cut.getWidth()).isEqualTo(3);
        assertThat(cut.getHeight()).isEqualTo(5);
        assertThat(cut.get(new Point(1,1))).isEqualTo(0);
        assertThat(cut.getWidth()).isEqualTo(3);
        assertThat(cut.getHeight()).isEqualTo(5);
    }

    @Test
    public void testGetPutGet() {
        assertThat(cut.get(new Point(2,4))).isEqualTo(0);
        cut.put(new Point(2,4),4);
        assertThat(cut.get(new Point(2,4))).isEqualTo(4);

    }

    @Test
    public void testPutGet() {
        cut.put(new Point(2,4),4);
        assertThat(cut.get(new Point(2,4))).isEqualTo(4);
    }

    @Test
    public void resize() {
        cut.put(new Point(2,2),4);
        cut.put(new Point(2,4),4);
        cut.put(new Point(4,4),4);
        cut.put(new Point(4,2),4);
        assertThat(cut.get(new Point(2,2))).isEqualTo(4);
        assertThat(cut.get(new Point(2,4))).isEqualTo(4);
        assertThat(cut.get(new Point(4,4))).isEqualTo(4);
        assertThat(cut.get(new Point(4,2))).isEqualTo(4);
        cut.setWidth(3);
        assertThat(cut.get(new Point(2,2))).isEqualTo(4);
        assertThat(cut.get(new Point(2,4))).isEqualTo(4);
        assertThat(cut.get(new Point(4,4))).isEqualTo(0);
        assertThat(cut.get(new Point(4,2))).isEqualTo(0);
        cut.setHeight(3);
        assertThat(cut.get(new Point(2,2))).isEqualTo(4);
        assertThat(cut.get(new Point(2,4))).isEqualTo(0);
        assertThat(cut.get(new Point(4,4))).isEqualTo(0);
        assertThat(cut.get(new Point(4,2))).isEqualTo(0);
    }

    @Test
    public void everPut() {
        assertThat(cut.get(2,2)).isEqualTo(0);
        assertThat(cut.everPut(new Point(2,2))).isEqualTo(false);
        cut.put(new Point(2,2),0);
        assertThat(cut.get(2,2)).isEqualTo(0);
        assertThat(cut.everPut(new Point(2,2))).isEqualTo(true);
    }


}
