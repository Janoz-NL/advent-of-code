package com.janoz.aoc;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class InputProcessorTest {

    @Test
    void testIterator() {
        Iterator<Integer> cut = new InputProcessor<>("list.txt", Integer::valueOf).iterator();

        assertThat(cut.hasNext(), is(true));
        assertThat(cut.next(), equalTo(1));
        assertThat(cut.hasNext(), is(true));
        assertThat(cut.next(), equalTo(2));
        assertThat(cut.hasNext(), is(true));
        assertThat(cut.next(), equalTo(3));
        assertThat(cut.hasNext(), is(false));
    }

    @Test
    void testList() {
        List<Integer> actual = new InputProcessor<>("list.txt", Integer::valueOf).asList();

        assertThat(actual.size(), is(3));
        assertThat(actual.toArray(), equalTo(new Integer[]{1,2,3}));
    }

}
