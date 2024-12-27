package com.janoz.aoc.y2021.day2;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MovementTest {


    @Test
    public void testConstructor() {
        Movement m = new Movement("forward 2");
        assertThat(m.direction).isEqualTo(Movement.Direction.FORWARD);
        assertThat(m.length).isEqualTo(2);
    }
}
