package com.janoz.aoc.y2015.day20;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day20Test {

    @Test
    void testPresents() {
        assertThat(Day20.presents(1)).isEqualTo(10);
        assertThat(Day20.presents(2)).isEqualTo(30);
        assertThat(Day20.presents(3)).isEqualTo(40);
        assertThat(Day20.presents(4)).isEqualTo(70);
        assertThat(Day20.presents(5)).isEqualTo(60);
        assertThat(Day20.presents(6)).isEqualTo(120);
        assertThat(Day20.presents(7)).isEqualTo(80);
        assertThat(Day20.presents(8)).isEqualTo(150);
        assertThat(Day20.presents(9)).isEqualTo(130);
    }

}