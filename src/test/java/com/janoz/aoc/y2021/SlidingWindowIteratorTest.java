package com.janoz.aoc.y2021;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


class SlidingWindowIteratorTest {

    SlidingWindowIterator cut;

    @Test
    void testSLidingWindow() {
        cut = new SlidingWindowIterator(IntStream.of(199,200,208,210,200,207,240,269,260,263).boxed().iterator());
        assertThat(cut.next(), is(607));
        assertThat(cut.next(), is(618));
        assertThat(cut.next(), is(618));
        assertThat(cut.next(), is(617));
        assertThat(cut.next(), is(647));
        assertThat(cut.next(), is(716));
        assertThat(cut.next(), is(769));
        assertThat(cut.next(), is(792));
        assertThat(cut.hasNext(), is(false));
    }

}