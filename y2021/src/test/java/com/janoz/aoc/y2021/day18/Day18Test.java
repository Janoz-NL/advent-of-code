package com.janoz.aoc.y2021.day18;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day18Test {


    @Test
    void testPart1Sum() {
        assertThat(Day18.part1("inputs/day18example1.txt").toString()).isEqualTo("[[[[1,1],[2,2]],[3,3]],[4,4]]");
        assertThat(Day18.part1("inputs/day18example2.txt").toString()).isEqualTo("[[[[3,0],[5,3]],[4,4]],[5,5]]");
        assertThat(Day18.part1("inputs/day18example3.txt").toString()).isEqualTo("[[[[5,0],[7,4]],[5,5]],[6,6]]");
        assertThat(Day18.part1("inputs/day18example4.txt").toString()).isEqualTo("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]");
    }

    @Test
    void testPart1Magnitude() {
        assertThat(Day18.part1("inputs/day18example.txt").magnitude()).isEqualTo(4140L);
    }
}