package com.janoz.aoc.y2015.day25;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day25Test {

    @Test
    void testTriangle() {
        for (int y=1; y <= 6; y ++) {
            for (int x=1; x <= 6; x ++) {
                System.out.print(Day25.getValue(x,y));
                System.out.print("\t");
            }
            System.out.println();
        }
    }

    @Test
    void testSteps() {
        assertThat(Day25.step(20151125L)).isEqualTo(31916031L);
        assertThat(Day25.step(31916031L)).isEqualTo(18749137L);
    }
}