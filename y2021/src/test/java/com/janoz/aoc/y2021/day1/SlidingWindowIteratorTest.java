package com.janoz.aoc.y2021.day1;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class SlidingWindowIteratorTest {

    SlidingWindowIterator cut;

    @Test
    void testSLidingWindow() {
        cut = new SlidingWindowIterator(IntStream.of(199,200,208,210,200,207,240,269,260,263).boxed().iterator());
        assertThat(cut.next()).isEqualTo(607);
        assertThat(cut.next()).isEqualTo(618);
        assertThat(cut.next()).isEqualTo(618);
        assertThat(cut.next()).isEqualTo(617);
        assertThat(cut.next()).isEqualTo(647);
        assertThat(cut.next()).isEqualTo(716);
        assertThat(cut.next()).isEqualTo(769);
        assertThat(cut.next()).isEqualTo(792);
        assertThat(cut.hasNext()).isFalse();
    }

}