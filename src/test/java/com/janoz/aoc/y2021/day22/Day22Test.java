package com.janoz.aoc.y2021.day22;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class Day22Test {


    @Test
    void testParseCube() {
        Day22.BoolCube c = Day22.BoolCube.parse("on x=-20..26,y=-36..17,z=-47..7");
        assertThat(c.state, equalTo(true));
        assertThat(c.cube.min.x, equalTo(-20L));
        assertThat(c.cube.max.z, equalTo(8L));
    }

    @Test
    void testPart1() {
        Day22.part1("inputs/day22example.txt");
        assertThat(Day22.reactor.stream().mapToLong(Cube::volumne).sum(), equalTo(474140L));
    }

    @Test
    void testPart2() {
        Day22.part2("inputs/day22example.txt");
        assertThat(Day22.reactor.stream().mapToLong(Cube::volumne).sum(), equalTo(2758514936282235L));
    }

}