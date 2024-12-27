package com.janoz.aoc.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class SortedBagTest {

    SortedBag<Long, Long> cut;

    @BeforeEach
    void setup() {
        cut = SortedBag.longSortedBag();
    }

    @Test
    void testStats() {
        Arrays.asList(1L,1L,2L,1L).forEach(l -> cut.put(l));
        assertThat(cut.min()).isEqualTo(1L);
        assertThat(cut.max()).isEqualTo(2L);
        assertThat(cut.size()).isEqualTo(4L);
        assertThat(cut.sum()).isEqualTo(5L);
    }

    @Test
    void testGet() {
        Arrays.asList(1L,4L,2L,2L,1L,2L).forEach(l -> cut.put(l));
        assertThat(cut.get(0L)).isEqualTo(1L);
        assertThat(cut.get(1L)).isEqualTo(1L);
        assertThat(cut.get(2L)).isEqualTo(2L);
        assertThat(cut.get(3L)).isEqualTo(2L);
        assertThat(cut.get(4L)).isEqualTo(2L);
        assertThat(cut.get(5L)).isEqualTo(4L);
    }
}