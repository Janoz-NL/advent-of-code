package com.janoz.aoc.y2021.day2;

import com.janoz.aoc.InputProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

public class Day2Test {

    private Day2 cut;

    @BeforeEach
    void before() {
        cut = new Day2();
    }

    @Test
    public void testPart1() {
        int result = cut.determinePosition1(new InputProcessor<>("inputs/day2example.txt", Movement::new));
        assertThat(result, equalTo(150));
    }

    @Test
    public void testPart2() {
        int result = cut.determinePosition2(new InputProcessor<>("inputs/day2example.txt", Movement::new));
        assertThat(result, equalTo(900));
    }

}
