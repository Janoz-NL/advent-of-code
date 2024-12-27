package com.janoz.aoc.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class MergingMapTest {

    MergingMap cut;

    @BeforeEach
    public void init(){
        cut = new MergingMap();
    }

    @Test
    public void test() {
        cut.addMapping(1,2);
        cut.addMapping(2,3);
        cut.addMapping(1,3);
        assertThat(cut.getActual(1)).isEqualTo(1);
        assertThat(cut.getActual(2)).isEqualTo(1);
        assertThat(cut.getActual(3)).isEqualTo(1);
        assertThat(cut.getActual(4)).isEqualTo(4);

    }

    @Test
    public void testEdgeCaseDay9() {
        cut.addMapping(150,141);
        cut.addMapping(150,142);
        assertThat(cut.getActual(150)).isEqualTo(141);
        assertThat(cut.getActual(142)).isEqualTo(141);
        assertThat(cut.getActual(141)).isEqualTo(141);

    }

}