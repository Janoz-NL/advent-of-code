package com.janoz.aoc.y2021.day22;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day22Test {


    @Test
    void testParseCube() {
        Day22.BoolCube c = Day22.BoolCube.parse("on x=-20..26,y=-36..17,z=-47..7");
        assertThat(c.state).isTrue();
        assertThat(c.cube.min.x).isEqualTo(-20L);
        assertThat(c.cube.max.z).isEqualTo(8L);
    }

    @Test
    void testPart1() {
        Day22.part1("inputs/day22example.txt");
        assertThat(Day22.reactor.stream().mapToLong(Cube::volumne).sum()).isEqualTo(474140L);
    }

    @Test
    void testPart2() {
        Day22.part2("inputs/day22example.txt");
        assertThat(Day22.reactor.stream().mapToLong(Cube::volumne).sum()).isEqualTo(2758514936282235L);
    }

}