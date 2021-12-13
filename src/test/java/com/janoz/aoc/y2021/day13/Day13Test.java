package com.janoz.aoc.y2021.day13;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class Day13Test {



    @Test
    public void testFoldY() {
        Day13 d = new Day13();
        d.part1("inputs/day13example.txt");
        assertThat(d.count(), equalTo(17L));
    }
}