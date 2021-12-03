package com.janoz.aoc.y2021.day3;

import com.janoz.aoc.InputIterable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

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
        List<boolean[]> actual = cut.filter(getTestInputAsList(), 0);
        assertThat(7, equalTo(actual.size()));
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
        return getTestInput().stream().collect(Collectors.toList());
    }


    private InputIterable<boolean[]> getTestInput() {
        return new InputIterable<>("inputs/day3example.txt", new BitMapper());
    }

}
