package com.janoz.aoc.y2022.day6;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class Day06Test {

    @Test
    void testExamples1() {
        assertThat(Day06.solve("mjqjpqmgbljsphdztnvjfqwrcgsmlb",4)).isEqualTo(7);
        assertThat(Day06.solve("bvwbjplbgvbhsrlpgdmjqwftvncz",4)).isEqualTo(5);
        assertThat(Day06.solve("nppdvjthqldpwncqszvftbrmjlhg",4)).isEqualTo(6);
        assertThat(Day06.solve("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg",4)).isEqualTo(10);
        assertThat(Day06.solve("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw",4)).isEqualTo(11);
    }

    @Test
    void testExamples2() {
        assertThat(Day06.solve("mjqjpqmgbljsphdztnvjfqwrcgsmlb",14)).isEqualTo(19);
        assertThat(Day06.solve("bvwbjplbgvbhsrlpgdmjqwftvncz",14)).isEqualTo(23);
        assertThat(Day06.solve("nppdvjthqldpwncqszvftbrmjlhg",14)).isEqualTo(23);
        assertThat(Day06.solve("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg",14)).isEqualTo(29);
        assertThat(Day06.solve("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw",14)).isEqualTo(26);
    }


}