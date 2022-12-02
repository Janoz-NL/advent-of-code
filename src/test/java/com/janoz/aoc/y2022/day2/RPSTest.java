package com.janoz.aoc.y2022.day2;

import org.junit.jupiter.api.Test;

import static com.janoz.aoc.y2022.day2.RPS.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RPSTest {

    @Test
    void testWins() {
        assertThat(ROCK.battle(ROCK), is(0));
        assertThat(ROCK.battle(SCISSOR), is(1));
        assertThat(SCISSOR.battle(ROCK), is(-1));
        assertThat(SCISSOR.battle(PAPER), is(1));
        assertThat(PAPER.battle(ROCK), is(1));



        assertThat(ROCK.battle(PAPER), is(-1));
        assertThat(PAPER.battle(SCISSOR), is(-1));
        assertThat(SCISSOR.battle(PAPER), is(1));

    }
}
