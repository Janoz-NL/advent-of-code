package com.janoz.aoc.y2021.day3;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BitMapperTest {

    @Test
    public void testBitmapper() {
        boolean[] actual = new BitMapper().apply("0011");
        assertThat(actual).isEqualTo(new boolean[]{false,false,true,true});

    }

}