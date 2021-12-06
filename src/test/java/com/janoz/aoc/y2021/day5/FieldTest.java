package com.janoz.aoc.y2021.day5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FieldTest {

    Field<Integer> cut;

    @BeforeEach
    public void before() {
        cut = new Field<>(0);
    }

    @Test
    public void testResize() {
        assertThat(cut.getWidth(), is(0));
        assertThat(cut.getHeight(), is(0));
        assertThat(cut.get(new Point(2,4)), equalTo(0));
        assertThat(cut.getWidth(), is(3));
        assertThat(cut.getHeight(), is(5));
    }

    @Test
    public void testGetPutGet() {
        assertThat(cut.get(new Point(2,4)), equalTo(0));
        cut.put(new Point(2,4),4);
        assertThat(cut.get(new Point(2,4)), equalTo(4));

    }

    @Test
    public void testPutGet() {
        cut.put(new Point(2,4),4);
        assertThat(cut.get(new Point(2,4)), equalTo(4));
    }

}
