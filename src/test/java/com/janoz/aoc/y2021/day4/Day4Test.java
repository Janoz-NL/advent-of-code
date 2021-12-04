package com.janoz.aoc.y2021.day4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class Day4Test {


    Day4 cut;

    @BeforeEach
    public void before() {
        cut = new Day4();
    }

    @Test
    public void testInitializing() throws IOException {
        cut.init("inputs/day4example.txt");
        assertThat(cut.winner.winsInTurn, equalTo(11));
    }

    @Test
    public void testBoardScore() throws IOException {
        cut.init("inputs/day4example.txt");
        assertThat(cut.winner.getScore(), equalTo(188));
    }

    @Test
    public void testScoreWinner() throws IOException {
        cut.init("inputs/day4example.txt");
        assertThat(cut.getScore(cut.winner), equalTo(4512));
    }

    @Test
    public void testScoreLoser() throws IOException {
        cut.init("inputs/day4example.txt");
        assertThat(cut.getScore(cut.loser), equalTo(1924));
    }


}