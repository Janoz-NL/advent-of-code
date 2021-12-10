package com.janoz.aoc.collections;

import com.janoz.aoc.collections.SortedBag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class SortedBagTest {

    SortedBag<Long, Long> cut;

    @BeforeEach
    void setup() {
        cut = SortedBag.longSortedBag();
    }

    @Test
    void testStats() {
        Arrays.asList(1L,1L,2L,1L).forEach(l -> cut.put(l));
        assertThat(cut.min(), equalTo(1L));
        assertThat(cut.max(), equalTo(2L));
        assertThat(cut.size(), equalTo(4L));
        assertThat(cut.sum(), equalTo(5L));
    }

    @Test
    void testGet() {
        Arrays.asList(1L,4L,2L,2L,1L,2L).forEach(l -> cut.put(l));
        assertThat(cut.get(0L), equalTo(1L));
        assertThat(cut.get(1L), equalTo(1L));
        assertThat(cut.get(2L), equalTo(2L));
        assertThat(cut.get(3L), equalTo(2L));
        assertThat(cut.get(4L), equalTo(2L));
        assertThat(cut.get(5L), equalTo(4L));
    }
}