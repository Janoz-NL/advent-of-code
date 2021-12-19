package com.janoz.aoc.y2021.day18;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class TokenizerTest {

    Tokenizer cut;

    @BeforeEach
    void setup() {
        cut = new Tokenizer("con");
    }

    @Test
    void testTokenizer() {
        assertThat(cut.peek(), equalTo('c'));
        assertThat(cut.peek(), equalTo('c'));
        assertThat(cut.get(), equalTo('c'));
        assertThat(cut.peek(), equalTo('o'));
        assertThat(cut.get(), equalTo('o'));

        assertThat(cut.hasNext(), equalTo(true));
        assertThat(cut.get(), equalTo('n'));
        assertThat(cut.hasNext(), equalTo(false));
    }
}