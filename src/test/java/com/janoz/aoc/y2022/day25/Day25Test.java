package com.janoz.aoc.y2022.day25;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day25Test {

    @Test
    void testParseFromSNAFU() {
        assertThat(Day25.fromSnafu("1")).isEqualTo(1L);
        assertThat(Day25.fromSnafu("2")).isEqualTo(2L);
        assertThat(Day25.fromSnafu("1=")).isEqualTo(3L);
        assertThat(Day25.fromSnafu("1-")).isEqualTo(4L);
        assertThat(Day25.fromSnafu("10")).isEqualTo(5L);
        assertThat(Day25.fromSnafu("11")).isEqualTo(6L);
        assertThat(Day25.fromSnafu("12")).isEqualTo(7L);
        assertThat(Day25.fromSnafu("2=")).isEqualTo(8L);
        assertThat(Day25.fromSnafu("2-")).isEqualTo(9L);
        assertThat(Day25.fromSnafu("20")).isEqualTo(10L);
        assertThat(Day25.fromSnafu("1=0")).isEqualTo(15L);
        assertThat(Day25.fromSnafu("1-0")).isEqualTo(20L);
        assertThat(Day25.fromSnafu("1=11-2")).isEqualTo(2022L);
        assertThat(Day25.fromSnafu("1-0---0")).isEqualTo(12345L);
        assertThat(Day25.fromSnafu("1121-1110-1=0")).isEqualTo(314159265L);
    }

    @Test
    void testParseFToSNAFU() {
        assertThat(Day25.toSnafu(1L)).isEqualTo("1");
        assertThat(Day25.toSnafu(2L)).isEqualTo("2");
        assertThat(Day25.toSnafu(3L)).isEqualTo("1=");
        assertThat(Day25.toSnafu(4L)).isEqualTo("1-");
        assertThat(Day25.toSnafu(5L)).isEqualTo("10");
        assertThat(Day25.toSnafu(6L)).isEqualTo("11");
        assertThat(Day25.toSnafu(7L)).isEqualTo("12");
        assertThat(Day25.toSnafu(8L)).isEqualTo("2=");
        assertThat(Day25.toSnafu(9L)).isEqualTo("2-");
        assertThat(Day25.toSnafu(10L)).isEqualTo("20");
        assertThat(Day25.toSnafu(15L)).isEqualTo("1=0");
        assertThat(Day25.toSnafu(20L)).isEqualTo("1-0");
        assertThat(Day25.toSnafu(2022L)).isEqualTo("1=11-2");
        assertThat(Day25.toSnafu(12345L)).isEqualTo("1-0---0");
        assertThat(Day25.toSnafu(314159265L)).isEqualTo("1121-1110-1=0");
    }

    @Test
    void testSingle() {
        assertThat(Day25.toSnafu(3896492942L)).isEqualTo("1=10-0001=--==2");
        assertThat(Day25.fromSnafu("1=10-0001=--==2")).isEqualTo(3896492942L);

    }

}