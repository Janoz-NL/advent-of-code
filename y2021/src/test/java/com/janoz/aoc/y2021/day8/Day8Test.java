package com.janoz.aoc.y2021.day8;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class Day8Test {


    @Test
    void testMinus() {
        assertThat(Day8.minus("abcde","bd")).isEqualTo("ace");
        assertThat(Day8.minus("abcde","ab")).isEqualTo("cde");
        assertThat(Day8.minus("abcde","de")).isEqualTo("abc");
        assertThat(Day8.minus("bd","abcde")).isEqualTo("");
        assertThat(Day8.minus("ab","abcde")).isEqualTo("");
        assertThat(Day8.minus("de","abcde")).isEqualTo("");
    }

    @Test
    void testContains() {
        assertThat(Day8.containsAll("abcde","bd")).isEqualTo(true);
        assertThat(Day8.containsAll("abcde","ab")).isEqualTo(true);
        assertThat(Day8.containsAll("abcde","de")).isEqualTo(true);
        assertThat(Day8.containsAll("abcde","f")).isEqualTo(false);
        assertThat(Day8.containsAll("acdfg","ef")).isEqualTo(false);
    }


    @Test
    void testMap() {
        Function<String, Integer> func = s -> Day8.calc(s,Day8::map);
        assertThat(func.apply("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf" )).isEqualTo(5353);
        assertThat(func.apply("be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe")).isEqualTo(8394);
        assertThat(func.apply("edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc")).isEqualTo(9781);
        assertThat(func.apply("fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg")).isEqualTo(1197);
    }

}