package com.janoz.aoc.y2021.day3;

import com.janoz.aoc.InputIterable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;


public class Day3Test {

    private Day3 cut;

    @BeforeEach
    void before() {
        cut = new Day3(5);
    }

    @Test
    public void testPart1() {
        int actual = cut.calculatePowerConsumption(new InputIterable<>("inputs/day3example.txt", new BitMapper()));
        assertThat(actual, equalTo(198));

    }
}
