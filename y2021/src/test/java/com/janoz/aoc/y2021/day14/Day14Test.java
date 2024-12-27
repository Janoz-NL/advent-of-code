package com.janoz.aoc.y2021.day14;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

class Day14Test {


    @Test
    void testPart1() throws Exception {
        assertThat(Day14.replace("inputs/day14example.txt", 10)).isEqualTo(BigInteger.valueOf(1588L));
    }

    @Test
    void testPart2() throws Exception {
        assertThat(Day14.replace("inputs/day14example.txt", 40)).isEqualTo(BigInteger.valueOf(2188189693529L));
    }

    @Test
    void testPart2Ante() throws Exception {
        System.out.println(Day14.replace("inputs/day14example.txt", 50000));
    }



}