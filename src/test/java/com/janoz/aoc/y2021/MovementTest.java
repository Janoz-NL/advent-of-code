package com.janoz.aoc.y2021;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MovementTest {


    @Test
    public void testConstructor() {
        Movement m = new Movement("forward 2");
        assertThat(m.direction, is(Movement.Direction.FORWARD));
        assertThat(m.length, equalTo(2));
    }
}
