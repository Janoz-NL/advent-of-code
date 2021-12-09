package com.janoz.aoc.y2021.day8;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class Day8Test {


    @Test
    void testMinus() {
        assertThat(Day8.minus("abcde","bd"), equalTo("ace"));
        assertThat(Day8.minus("abcde","ab"), equalTo("cde"));
        assertThat(Day8.minus("abcde","de"), equalTo("abc"));
        assertThat(Day8.minus("bd","abcde"), equalTo(""));
        assertThat(Day8.minus("ab","abcde"), equalTo(""));
        assertThat(Day8.minus("de","abcde"), equalTo(""));
    }

    @Test
    void testContains() {
        assertThat(Day8.containsAll("abcde","bd"), equalTo(true));
        assertThat(Day8.containsAll("abcde","ab"), equalTo(true));
        assertThat(Day8.containsAll("abcde","de"), equalTo(true));
        assertThat(Day8.containsAll("abcde","f"), equalTo(false));
        assertThat(Day8.containsAll("acdfg","ef"), equalTo(false));
    }


    @Test
    void testMap() {
        Function<String, Integer> func = s -> Day8.calc(s,Day8::map);
        assertThat(func.apply("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf" ), equalTo(5353));
        assertThat(func.apply("be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe"), equalTo(8394));
        assertThat(func.apply("edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc"), equalTo(9781));
        assertThat(func.apply("fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg"), equalTo(1197));
    }

}