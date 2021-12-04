package com.janoz.aoc.y2021.day3;

import com.janoz.aoc.InputProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;


public class Day3Test {

    private Day3 cut;

    @BeforeEach
    void before() {
        cut = new Day3(5);
    }

    @Test
    public void testPart2() {
        int actual = cut.part2("inputs/day3example.txt");
        assertThat(actual, equalTo(230));

    }

    @Test
    public void testOxygen() {
        int actual = cut.calculateOxygen(getTestInputAsList());
        assertThat(actual, equalTo(23));
    }

    @Test
    public void testCo2() {
        int actual = cut.calculateCo2(getTestInputAsList());
        assertThat(actual, equalTo(10));
    }

    @Test
    public void testFilter() {
        assertThat(cut.filter(getTestInputAsList(), 0, true).size(), equalTo(7));
        assertThat(cut.filter(getTestInputAsList(), 0, false).size(), equalTo(5));
        assertThat(cut.filter(getTestInputAsList(), 4, true).size(), equalTo(5));
        assertThat(cut.filter(getTestInputAsList(), 4, false).size(), equalTo(7));
    }

    @Test
    public void testModusBits() {
        boolean[] actual =  cut.modusBits(getTestInput());
        assertThat(actual, equalTo(new boolean[]{true,false,true,true,false}));
    }

    @Test
    public void testPart1() {
        int actual = cut.part1("inputs/day3example.txt");
        assertThat(actual, equalTo(198));

    }

    private List<boolean[]> getTestInputAsList() {
        return getTestInput().asList();
    }

    private InputProcessor<boolean[]> getTestInput() {
        return new InputProcessor<>("inputs/day3example.txt", new BitMapper());
    }

}
