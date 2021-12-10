package com.janoz.aoc.y2021.day10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class Day10Test {

    Day10 cut;


    @BeforeEach
    public void before() {
        cut = new Day10();
    }


    @Test
    void testMap() {
        assertThat(cut.map('('), equalTo(')'));
        assertThat(cut.map('{'), equalTo('}'));
        assertThat(cut.map('['), equalTo(']'));
        assertThat(cut.map('<'), equalTo('>'));
    }

    @Test
    void testScore() {
        assertThat(cut.score("{([(<{}[<>[]}>{[]{[(<()>"), equalTo(1197L));
        assertThat(cut.score("[[<[([]))<([[{}[[()]]]"), equalTo(3L));
        assertThat(cut.score("[{[{({}]{}}([{[{{{}}([]"), equalTo(57L));
        assertThat(cut.score("[<(<(<(<{}))><([]([]()"), equalTo(3L));
        assertThat(cut.score("<{([([[(<>()){}]>(<<{{"), equalTo(25137L));
    }

    @Test
    void testScore2() {
        cut.score("[({(<(())[]>[[{[]{<()<>>");
        assertThat(cut.part2Score.get(0L), equalTo(288957L));
    }


    @Test
    void testExample() {
        assertThat(cut.process("inputs/day10example.txt"), equalTo(26397L));
        assertThat(cut.getPart2Score(), equalTo(288957L));

    }

}