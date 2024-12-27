package com.janoz.aoc.y2021.day3;

import java.util.function.Function;

public class BitMapper implements Function<String, boolean[]> {

    @Override
    public boolean[] apply(String input) {
        boolean[] result = new boolean[input.length()];
        for (int i=0;i<input.length();i++) {
            result[i] = input.charAt(i) == '1';
        }
        return result;
    }

}
