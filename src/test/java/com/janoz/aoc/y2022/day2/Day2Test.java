package com.janoz.aoc.y2022.day2;

import org.junit.jupiter.api.Test;

import static com.janoz.aoc.y2022.day2.RPS.*;
import static org.assertj.core.api.Assertions.assertThat;

public class Day2Test {
    
    
    
    
    @Test
    void testScoreCounter() {
        assertThat(Day2.score(Day2.mapToRPS("A Y"))).isEqualTo(8);
        assertThat(Day2.score(Day2.mapToRPS("B X"))).isEqualTo(1);
        assertThat(Day2.score(Day2.mapToRPS("C Z"))).isEqualTo(6);
    }

    @Test
    void testScoreWithOutcomeCounter() {
        assertThat(Day2.score(Day2.mapToOutcome("A Y"))).isEqualTo(4);
        assertThat(Day2.score(Day2.mapToOutcome("B X"))).isEqualTo(1);
        assertThat(Day2.score(Day2.mapToOutcome("C Z"))).isEqualTo(7);
    }

    @Test
    void testScore() {
        assertThat(Day2.score(ROCK, ROCK)).isEqualTo(1+3);
        assertThat(Day2.score(ROCK, PAPER)).isEqualTo(2+6);
        assertThat(Day2.score(ROCK, SCISSOR)).isEqualTo(3);
        assertThat(Day2.score(PAPER, ROCK)).isEqualTo(1);
        assertThat(Day2.score(PAPER, PAPER)).isEqualTo(2+3);
        assertThat(Day2.score(PAPER, SCISSOR)).isEqualTo(3+6);
    }


    /* * * * * * * * * * * * * * * * * * * * */
    @Test
    void constructTables() {
        int[] part1 = new int[9];
        int[] part2 = new int[9];
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                part1[i+(j*3)] = Day2.score(RPS.values()[i], RPS.values()[j]);
                part2[i+(j*3)] = Day2.score(RPS.values()[i], RPS.values()[i].withOutcome((char)('X' + j)));
            }
        }

        return;
    }


}
