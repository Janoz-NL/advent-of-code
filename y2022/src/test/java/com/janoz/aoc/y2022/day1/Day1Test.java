package com.janoz.aoc.y2022.day1;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class Day1Test {


    @Test
    void testStore() {
        int[] top3 = new int[]{5,3,1};
        Day1.store(top3,0);
        assertThat(top3).contains(5,3,1);
        Day1.store(top3,2);
        assertThat(top3).contains(5,3,2);
        Day1.store(top3,4);
        assertThat(top3).contains(5,4,3);
        Day1.store(top3,6);
        assertThat(top3).contains(6,5,4);
        Day1.store(top3,5);
        assertThat(top3).contains(6,5,5);
        Day1.store(top3,6);
        assertThat(top3).contains(6,6,5);
        Day1.store(top3,6);
        assertThat(top3).contains(6,6,6);
        Day1.store(top3,5);
        assertThat(top3).contains(6,6,6);
    }

}