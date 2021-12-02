package com.janoz.aoc.y2021;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class Day1Test {

    private Day1 cut;

    @BeforeEach
    void before() {
        cut = new Day1();
    }

    @Test
    void testGivenIncrementExample() {
        Iterator<Integer> input = IntStream.of(199,200,208,210,200,207,240,269,260,263).boxed().iterator();
        assertThat(cut.countIncreases(input), equalTo(7));
    }

    @Test
    void testGivenFilteredExample() {
        Iterator<Integer> input = IntStream.of(199,200,208,210,200,207,240,269,260,263).boxed().iterator();
        assertThat(cut.countIncreases(new SlidingWindowIterator(input)), equalTo(5));

    }
}
