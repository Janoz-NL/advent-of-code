package com.janoz.aoc.y2021.day10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day10Test {

    Day10 cut;


    @BeforeEach
    public void before() {
        cut = new Day10();
    }


    @Test
    void testMap() {
        assertThat(cut.map('(')).isEqualTo(')');
        assertThat(cut.map('{')).isEqualTo('}');
        assertThat(cut.map('[')).isEqualTo(']');
        assertThat(cut.map('<')).isEqualTo('>');
    }

    @Test
    void testScore() {
        assertThat(cut.score("{([(<{}[<>[]}>{[]{[(<()>")).isEqualTo(1197L);
        assertThat(cut.score("[[<[([]))<([[{}[[()]]]")).isEqualTo(3L);
        assertThat(cut.score("[{[{({}]{}}([{[{{{}}([]")).isEqualTo(57L);
        assertThat(cut.score("[<(<(<(<{}))><([]([]()")).isEqualTo(3L);
        assertThat(cut.score("<{([([[(<>()){}]>(<<{{")).isEqualTo(25137L);
    }

    @Test
    void testScore2() {
        cut.score("[({(<(())[]>[[{[]{<()<>>");
        assertThat(cut.part2Score.get(0L)).isEqualTo(288957L);
    }


    @Test
    void testExample() {
        assertThat(cut.process("inputs/day10example.txt")).isEqualTo(26397L);
        assertThat(cut.getPart2Score()).isEqualTo(288957L);

    }

}