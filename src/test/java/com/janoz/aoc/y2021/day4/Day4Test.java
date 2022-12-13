package com.janoz.aoc.y2021.day4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class Day4Test {


    Day4 cut;

    @BeforeEach
    public void before() {
        cut = new Day4(5);
    }

    @Test
    public void testInitializing() throws IOException {
        cut.process("inputs/day4example.txt");
        assertThat(cut.winner.winsInTurn).isEqualTo(11);
    }

    @Test
    public void testBoardScore() throws IOException {
        cut.process("inputs/day4example.txt");
        assertThat(cut.winner.getScore()).isEqualTo(188L);
    }

    @Test
    public void testScoreWinner() throws IOException {
        cut.process("inputs/day4example.txt");
        assertThat(cut.getScore(cut.winner)).isEqualTo(4512L);
    }

    @Test
    public void testScoreLoser() throws IOException {
        cut.process("inputs/day4example.txt");
        assertThat(cut.getScore(cut.loser)).isEqualTo(1924L);
    }


}