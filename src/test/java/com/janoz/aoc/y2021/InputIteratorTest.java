package com.janoz.aoc.y2021;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class InputIteratorTest {

    InputIterator cut;

    @Test
    void testListWithoutLastNewline() throws Exception{
        cut = new InputIterator("list.txt");

        assertThat(cut.hasNext(), is(true));
        assertThat(cut.next(), equalTo(1));
        assertThat(cut.hasNext(), is(true));
        assertThat(cut.next(), equalTo(2));
        assertThat(cut.hasNext(), is(true));
        assertThat(cut.next(), equalTo(3));
        assertThat(cut.hasNext(), is(false));
    }

    @Test
    void testListWithLastNewline() throws Exception{
        cut = new InputIterator("list.txt");

        assertThat(cut.hasNext(), is(true));
        assertThat(cut.next(), equalTo(1));
        assertThat(cut.hasNext(), is(false));
    }
}
