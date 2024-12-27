package com.janoz.aoc.y2024.day17;

import static org.assertj.core.api.Assertions.assertThat;

import com.janoz.aoc.math.IntMatrixUtils;

import org.junit.jupiter.api.Test;

class Day17Test {

    @Test
    void testTestInput() {
        ChronospatialComputer cc = new ChronospatialComputer(729,0,0,split("0,1,5,4,3,0"));
        assertThat(cc.run()).isEqualTo("4,6,3,5,6,3,5,2,1,0");

    }

    private int[] split(String s) {
        return IntMatrixUtils.readLine(s,",");
    }

    @Test
    void testPart1() {
        ChronospatialComputer cc = new ChronospatialComputer(38610541,0,0,split("2,4,1,1,7,5,1,5,4,3,5,5,0,3,3,0"));
        System.out.println(cc.run());
    }
}