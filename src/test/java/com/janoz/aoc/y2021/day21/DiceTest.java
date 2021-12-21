package com.janoz.aoc.y2021.day21;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class DiceTest {

    @Test
    void testRole() {
        Dice dice = new Dice();
        IntStream.rangeClosed(1,100).forEach(i -> assertThat(dice.next(), equalTo(i)));
        assertThat(dice.next(), equalTo(1));
        assertThat(dice.timesRolled, equalTo(101));
    }

}