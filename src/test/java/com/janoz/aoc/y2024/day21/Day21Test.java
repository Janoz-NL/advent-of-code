package com.janoz.aoc.y2024.day21;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day21Test {

    @Test
    void testGetSequence() {
        assertThat(Day21.getSequences('A','7',Day21.keypad, 3)).containsExactly("^^^<<A");
        assertThat(Day21.getSequences('7','A',Day21.keypad, 3)).containsExactly(">>vvvA");
    }

    @Test
    void testPart1Sequence() {
        assertThat(Day21.findLengthFromCode("029A",2)).isEqualTo(68);
        assertThat(Day21.findLengthFromCode("379A",2)).isEqualTo(64);

    }

    @Test
    void scoresPart1() {
        assertThat(Day21.score("029A",2)).isEqualTo(68*29);
        assertThat(Day21.score("980A",2)).isEqualTo(60*980);
        assertThat(Day21.score("179A",2)).isEqualTo(68*179);
        assertThat(Day21.score("456A",2)).isEqualTo(64*456);
        assertThat(Day21.score("379A",2)).isEqualTo(64*379);
    }

    @Test
    void testTweedePoging() {
        assertThat(Day21.findLengthFromCode("029A",25)).isEqualTo(82050061710L);
    }
}