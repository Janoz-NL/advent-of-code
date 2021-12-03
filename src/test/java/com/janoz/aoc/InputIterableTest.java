package com.janoz.aoc;

import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class InputIterableTest {

    @Test
    void testList() throws Exception{
        Iterator<Integer> cut = new InputIterable<>("list.txt", Integer::valueOf).iterator();

        assertThat(cut.hasNext(), is(true));
        assertThat(cut.next(), equalTo(1));
        assertThat(cut.hasNext(), is(true));
        assertThat(cut.next(), equalTo(2));
        assertThat(cut.hasNext(), is(true));
        assertThat(cut.next(), equalTo(3));
        assertThat(cut.hasNext(), is(false));
    }

}
