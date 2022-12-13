package com.janoz.aoc.y2022.day2;

import org.junit.jupiter.api.Test;

import static com.janoz.aoc.y2022.day2.RPS.*;
import static org.assertj.core.api.Assertions.assertThat;


public class RPSTest {

    @Test
    void testWins() {
        assertThat(ROCK.battle(ROCK)).isEqualTo(0);
        assertThat(ROCK.battle(SCISSOR)).isEqualTo(1);
        assertThat(SCISSOR.battle(ROCK)).isEqualTo(-1);
        assertThat(SCISSOR.battle(PAPER)).isEqualTo(1);
        assertThat(PAPER.battle(ROCK)).isEqualTo(1);

        assertThat(ROCK.battle(PAPER)).isEqualTo(-1);
        assertThat(PAPER.battle(SCISSOR)).isEqualTo(-1);
        assertThat(SCISSOR.battle(PAPER)).isEqualTo(1);

    }
}
