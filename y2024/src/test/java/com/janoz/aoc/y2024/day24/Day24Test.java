package com.janoz.aoc.y2024.day24;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day24Test {

    @Test
    void testOperations() {
        assertThat(Day24.AND.apply(true,true)).isTrue();
        assertThat(Day24.AND.apply(false,true)).isFalse();
        assertThat(Day24.OR.apply(false,true)).isTrue();
        assertThat(Day24.OR.apply(false,false)).isFalse();

        assertThat(Day24.XOR.apply(true,true)).isFalse();
        assertThat(Day24.XOR.apply(false,true)).isTrue();
        assertThat(Day24.XOR.apply(false,false)).isFalse();
    }

}