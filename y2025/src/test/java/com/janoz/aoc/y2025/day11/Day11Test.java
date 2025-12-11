package com.janoz.aoc.y2025.day11;

import com.janoz.aoc.input.AocInput;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day11Test {

    static final String INPUT = "aaa: you hhh\n" +
            "you: bbb ccc\n" +
            "bbb: ddd eee\n" +
            "ccc: ddd eee fff\n" +
            "ddd: ggg\n" +
            "eee: out\n" +
            "fff: out\n" +
            "ggg: out\n" +
            "hhh: ccc fff iii\n" +
            "iii: out";


    static final String INPUT2 = "svr: aaa bbb\n" +
            "aaa: fft\n" +
            "fft: ccc\n" +
            "bbb: tty\n" +
            "tty: ccc\n" +
            "ccc: ddd eee\n" +
            "ddd: hub\n" +
            "hub: fff\n" +
            "eee: dac\n" +
            "dac: fff\n" +
            "fff: ggg hhh\n" +
            "ggg: out\n" +
            "hhh: out";

    static final String INPUT3 = "svr: fft \n" +
            "fft: dac\n" +
            "dac: out";

    @Test
    void test() {
        Day11.printResult(Day11.solve(AocInput.ofString(INPUT3)));
    }
}