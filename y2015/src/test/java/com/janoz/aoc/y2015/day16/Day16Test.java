package com.janoz.aoc.y2015.day16;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day16Test {

    @Test
    void testAmt() {
        Day16.AntSue ant = new Day16.AntSue("Sue 1: goldfish: 6, trees: 9, akitas: 0");
        assertThat(ant.compounts.containsKey("goldfish"));

    }

}