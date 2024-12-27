package com.janoz.aoc;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class InputProcessorTest {

    @Test
    void testIterator() {
        Iterator<Integer> cut = InputProcessor.asIterator("list.txt", Integer::valueOf);

        assertThat(cut.hasNext()).isTrue();
        assertThat(cut.next()).isEqualTo(1);
        assertThat(cut.hasNext()).isTrue();
        assertThat(cut.next()).isEqualTo(2);
        assertThat(cut.hasNext()).isTrue();
        assertThat(cut.next()).isEqualTo(3);
        assertThat(cut.hasNext()).isFalse();
    }

    @Test
    void testList() {
        List<Integer> actual = InputProcessor.asIterable("list.txt", Integer::valueOf).asList();

        assertThat(actual.size()).isEqualTo(3);
        assertThat(actual.toArray()).isEqualTo(new Integer[]{1,2,3});
    }


    static String INPUT_WITH_NEWLINES =
            "1000\n" +
            "2000\n" +
            "3000\n" +
            "\n" +
            "4000\n" +
            "\n" +
            "5000\n" +
            "6000\n" +
            "\n" +
            "7000\n" +
            "8000\n" +
            "9000\n" +
            "\n" +
            "10000";

    @Test
    void testStopCriteria() {
        assertThat(new InputProcessor<>(new BufferedReader(new StringReader(INPUT_WITH_NEWLINES)),s->s, false).stream().count()).isEqualTo(14L);
        assertThat(new InputProcessor<>(new BufferedReader(new StringReader(INPUT_WITH_NEWLINES)),s->s, true).stream().count()).isEqualTo(3L);
    }
}
