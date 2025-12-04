package com.janoz.aoc.y2025.day4;

import com.janoz.aoc.collections.LongTuple;
import com.janoz.aoc.input.AocInput;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day4Test {

    static final String TEST_INPUT =
            """
                    ..@@.@@@@.
                    @@@.@.@.@@
                    @@@@@.@.@@
                    @.@@@@..@.
                    @@.@@@@.@@
                    .@@@@@@@.@
                    .@.@.@.@@@
                    @.@@@.@@@@
                    .@@@@@@@@.
                    @.@.@@@.@.""";

    @Test
    public void runTest() {
        LongTuple result = Day4.solve(AocInput.ofString(TEST_INPUT));
        assertThat(result.getLeft()).isEqualTo(13L);
        assertThat(result.getRight()).isEqualTo(43L);
    }
}