package com.janoz.aoc.y2021.day18;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Day18Test {


    @Test
    void testPart1Sum() {
        assertThat(Day18.part1("inputs/day18example1.txt").toString(), equalTo("[[[[1,1],[2,2]],[3,3]],[4,4]]"));
        assertThat(Day18.part1("inputs/day18example2.txt").toString(), equalTo("[[[[3,0],[5,3]],[4,4]],[5,5]]"));
        assertThat(Day18.part1("inputs/day18example3.txt").toString(), equalTo("[[[[5,0],[7,4]],[5,5]],[6,6]]"));
        assertThat(Day18.part1("inputs/day18example4.txt").toString(), equalTo("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]"));
    }

    @Test
    void testPart1Magnitude() {
        assertThat(Day18.part1("inputs/day18example.txt").magnitude(), equalTo(4140L));
    }
}