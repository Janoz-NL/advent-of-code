package com.janoz.aoc.y2021.day14;

import com.janoz.aoc.StopWatch;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class Day14Test {


    @Test
    void testPart1() throws Exception {
        assertThat(Day14.replace("inputs/day14example.txt", 10), equalTo(BigInteger.valueOf(1588L)));
    }

    @Test
    void testPart2() throws Exception {
        assertThat(Day14.replace("inputs/day14example.txt", 40), equalTo(BigInteger.valueOf(2188189693529L)));
    }

    @Test
    void testPart2Ante() throws Exception {
        System.out.println(Day14.replace("inputs/day14example.txt", 50000));
    }



}