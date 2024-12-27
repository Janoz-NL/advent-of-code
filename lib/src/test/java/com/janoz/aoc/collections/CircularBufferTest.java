package com.janoz.aoc.collections;


import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class CircularBufferTest {

    @Test
    void testReading() throws IOException {
        InputStream inputStream = new ByteArrayInputStream("atiaticunproblematicunproblematicundiscoverablyundiscoverably".getBytes());
        CircularBuffer cut = new CircularBuffer(inputStream, 5);
        assertThat(cut.readNext()).isEqualTo('a');
        assertThat(cut.readNext()).isEqualTo('t');
        assertThat(cut.readNext()).isEqualTo('i');
        assertThat(cut.readNext()).isEqualTo('a');
        assertThat(cut.readNext()).isEqualTo('t');
        assertThat(cut.readNext()).isEqualTo('i');
        assertThat(cut.readNext()).isEqualTo('c');

        assertThat(cut.get(0)).isEqualTo('c');
        assertThat(cut.get(1)).isEqualTo('i');
        assertThat(cut.get(2)).isEqualTo('t');
        assertThat(cut.get(3)).isEqualTo('a');
        assertThat(cut.get(4)).isEqualTo('i');
    }
}