package com.janoz.aoc.y2021.day3;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class BitMapperTest {

    @Test
    public void testBitmapper() {
        boolean[] actual = new BitMapper().apply("0011");
        assertThat(actual, equalTo(new boolean[]{false,false,true,true}));

    }

}