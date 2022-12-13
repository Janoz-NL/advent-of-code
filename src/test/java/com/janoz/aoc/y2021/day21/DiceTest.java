package com.janoz.aoc.y2021.day21;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class DiceTest {

    @Test
    void testRole() {
        Dice dice = new Dice();
        IntStream.rangeClosed(1,100).forEach(i -> assertThat(dice.next()).isEqualTo(i));
        assertThat(dice.next()).isEqualTo(1);
        assertThat(dice.timesRolled).isEqualTo(101);
    }

}