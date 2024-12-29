package com.janoz.aoc.input;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;

class AocInputTest {


    @Test
    void testParts() {
        AocInput<String> cut = AocInput.ofString(
                "Aap\n" +
                        "\n" +
                        "Noot");
        Iterator<String> iterator = cut.iterator();
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo("Aap");
        assertThat(iterator.hasNext()).isFalse();
        assertThat(cut.hasNextPart()).isTrue();
        iterator = cut.iterator();
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo("Noot");
        assertThat(iterator.hasNext()).isFalse();
        assertThat(cut.hasNextPart()).isFalse();
    }

    @Test
    @Disabled("Only works with valid session")
    void testDownload() {
        AocInput<String> cut = AocInput.of(2015, 4);
        Iterator<String> iterator = cut.iterator();
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo("yzbqklnj");
        assertThat(iterator.hasNext()).isFalse();
        assertThat(cut.hasNextPart()).isFalse();
    }

    @Test
    void urlTest() {
        assertThat(String.format("https://adventofcode.com/%s/day/%s/input", 2015, 4)).isEqualTo("https://adventofcode.com/2015/day/4/input");
    }
}