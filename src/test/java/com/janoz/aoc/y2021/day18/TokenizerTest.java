package com.janoz.aoc.y2021.day18;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TokenizerTest {

    Tokenizer cut;

    @BeforeEach
    void setup() {
        cut = new Tokenizer("con");
    }

    @Test
    void testTokenizer() {
        assertThat(cut.peek()).isEqualTo('c');
        assertThat(cut.peek()).isEqualTo('c');
        assertThat(cut.get()).isEqualTo('c');
        assertThat(cut.peek()).isEqualTo('o');
        assertThat(cut.get()).isEqualTo('o');

        assertThat(cut.hasNext()).isEqualTo(true);
        assertThat(cut.get()).isEqualTo('n');
        assertThat(cut.hasNext()).isEqualTo(false);
    }
}