package com.janoz.aoc.y2021.day22;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class CubeTest {


    @Test
    void testIntersects() {
        assertThat(new Cube(-2,-2,-2,2,2,2).intersects(new Cube(0,0,0,4,4,4)), equalTo(true));
        assertThat(new Cube(-2,-2,-2,2,2,2).intersects(new Cube(2,2,2,4,4,4)), equalTo(false));
        assertThat(new Cube(-2,-2,-2,2,2,2).intersects(new Cube(3,3,3,4,4,4)), equalTo(false));
    }

    @Test
    void testcontains() {
        assertThat(new Cube(-2,-2,-2,2,2,2).contains(new Cube(-2,-2,-2,2,2,2)), equalTo(true));
        assertThat(new Cube(-2,-2,-2,2,2,2).contains(new Cube(-1,-1,-1,1,1,1)), equalTo(true));
        assertThat(new Cube(-2,-2,-2,2,2,2).contains(new Cube(-3,-3,-3,3,3,3)), equalTo(false));
        assertThat(new Cube(-2,-2,-2,2,2,2).contains(new Cube(-1,-1,-1,3,3,3)), equalTo(false));
    }

    @Test
    void testCut() {
        Cube start = new Cube(-2, -2, -2, 2, 2, 2);
        Cube added = new Cube(0, 0, 0, 4, 4, 4);
        Set<Cube>[] cubes = start.cut(added);
        assertThat(cubes[0].stream().mapToLong(Cube::volumne).sum(), equalTo(start.volumne()));
        assertThat(cubes[0].stream().mapToLong(Cube::volumne).sum(), equalTo(added.volumne()));
    }


}