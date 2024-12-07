package com.janoz.aoc.geo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class DirectionTest {

    @Test
    void testRotateCW() {
        assertThat(Direction.NORTH.rotateCW()).isEqualTo(Direction.EAST);
        assertThat(Direction.EAST.rotateCW()).isEqualTo(Direction.SOUTH);
        assertThat(Direction.SOUTH.rotateCW()).isEqualTo(Direction.WEST);
        assertThat(Direction.WEST.rotateCW()).isEqualTo(Direction.NORTH);
    }

    @Test
    void testRotateCCW() {
        assertThat(Direction.NORTH.rotateCCW()).isEqualTo(Direction.WEST);
        assertThat(Direction.EAST.rotateCCW()).isEqualTo(Direction.NORTH);
        assertThat(Direction.SOUTH.rotateCCW()).isEqualTo(Direction.EAST);
        assertThat(Direction.WEST.rotateCCW()).isEqualTo(Direction.SOUTH);
    }
}
