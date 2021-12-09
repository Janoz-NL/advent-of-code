package com.janoz.aoc.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

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
        assertThat(cut.getActual(1), equalTo(1));
        assertThat(cut.getActual(2), equalTo(1));
        assertThat(cut.getActual(3), equalTo(1));
        assertThat(cut.getActual(4), equalTo(4));

    }

}