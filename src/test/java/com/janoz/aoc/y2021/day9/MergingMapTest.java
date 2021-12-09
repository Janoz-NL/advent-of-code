package com.janoz.aoc.y2021.day9;

import com.janoz.aoc.y2021.day8.Day8;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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