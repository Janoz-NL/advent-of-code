package com.janoz.aoc.y2022.day11;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day11Test {


    @Test
    public void validateSolutions() {
        assertThat(Day11.solve("inputs/2022/day11test.txt", 20, Day11.PART_1)).isEqualTo(10605L);
        assertThat(Day11.solve("inputs/2022/day11test.txt", 10000, Day11.PART_2)).isEqualTo(2713310158L);
    }
}
