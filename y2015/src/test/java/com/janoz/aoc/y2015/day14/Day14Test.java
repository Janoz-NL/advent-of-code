package com.janoz.aoc.y2015.day14;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day14Test {

    @Test
    void testReindeer() {
        Day14.Reindeer r = new Day14.Reindeer("Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.");
        assertThat(r.getPositionAt(1)).isEqualTo(14);
        assertThat(r.getPositionAt(1)).isEqualTo(14);
    }
}