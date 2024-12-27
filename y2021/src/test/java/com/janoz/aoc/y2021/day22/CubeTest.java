package com.janoz.aoc.y2021.day22;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CubeTest {


    @Test
    void testIntersects() {
        assertThat(new Cube(-2,-2,-2,2,2,2).intersects(new Cube(0,0,0,4,4,4))).isTrue();
        assertThat(new Cube(-2,-2,-2,2,2,2).intersects(new Cube(2,2,2,4,4,4))).isFalse();
        assertThat(new Cube(-2,-2,-2,2,2,2).intersects(new Cube(3,3,3,4,4,4))).isFalse();
    }

    @Test
    void testcontains() {
        assertThat(new Cube(-2,-2,-2,2,2,2).contains(new Cube(-2,-2,-2,2,2,2))).isTrue();
        assertThat(new Cube(-2,-2,-2,2,2,2).contains(new Cube(-1,-1,-1,1,1,1))).isTrue();
        assertThat(new Cube(-2,-2,-2,2,2,2).contains(new Cube(-3,-3,-3,3,3,3))).isFalse();
        assertThat(new Cube(-2,-2,-2,2,2,2).contains(new Cube(-1,-1,-1,3,3,3))).isFalse();
    }

    @Test
    void testCut() {
        Cube start = new Cube(-2, -2, -2, 2, 2, 2);
        Cube added = new Cube(0, 0, 0, 4, 4, 4);
        Set<Cube>[] cubes = start.cut(added);
        assertThat(cubes[0].stream().mapToLong(Cube::volumne).sum()).isEqualTo(start.volumne());
        assertThat(cubes[0].stream().mapToLong(Cube::volumne).sum()).isEqualTo(added.volumne());
    }


}