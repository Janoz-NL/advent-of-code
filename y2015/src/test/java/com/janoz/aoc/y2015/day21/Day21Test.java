package com.janoz.aoc.y2015.day21;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day21Test {

    @Test
    void testBattle() {
        Day21.boss = new Day21.Player(12, 5, 1);

        assertThat(Day21.doesPlayerWinBattle(
                new Day21.Player(100, 5, 5)
        )).isTrue();
        Day21.boss = new Day21.Player(11, 5, 1);
        assertThat(Day21.doesPlayerWinBattle(
                new Day21.Player(100, 5, 5)
        )).isTrue();
    }

    @Test
    void testTurns() {
        assertThat(Day21.deadInTurns(5,2)).isEqualTo(3);
        assertThat(Day21.deadInTurns(6,2)).isEqualTo(3);
        assertThat(Day21.deadInTurns(7,2)).isEqualTo(4);
    }

}