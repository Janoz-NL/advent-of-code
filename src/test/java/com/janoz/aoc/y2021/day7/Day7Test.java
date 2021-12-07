package com.janoz.aoc.y2021.day7;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class Day7Test {

    Day7 cut;

    @BeforeEach
    void init() {
        cut = new Day7();
        cut.initPositions(new long[]{16,1,2,0,4,2,7,1,2,14});
    }

    @Test
    void testPart1() {
        assertThat(cut.part1(), equalTo(37L));
    }

    @Test
    void testPart2() {
        assertThat(cut.part2b(), equalTo(168L));
    }


    @Test
    void testDataGhost() {
        cut.resetCalc();
        cut.initPositions("16,1,2,0,4,2,7,1,2,14,-50,-49,-48,-47,-46,-45,-44,-43,-42,-41,-37,-13");
        System.out.print(cut.part2b());
        System.out.println(" " + cut.calc);
        cut.resetCalc();
        cut.initPositions("16,1,2,0,4,2,7,1,2,14,999999999");
        System.out.print(cut.part2b());
        System.out.println(" " + cut.calc);
    }


    @Test
    void testCost2() {
        assertThat(cut.cost2(16,5), equalTo(66L) );
        assertThat(cut.cost2(1,5), equalTo(10L) );
        assertThat(cut.cost2(2,5), equalTo(6L) );
        assertThat(cut.cost2(0,5), equalTo(15L) );
        assertThat(cut.cost2(4,5), equalTo(1L) );
    }
}