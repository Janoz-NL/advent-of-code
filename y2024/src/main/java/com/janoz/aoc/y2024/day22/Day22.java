package com.janoz.aoc.y2024.day22;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.janoz.aoc.input.AocInput;

public class Day22 {

    static List<Long> initials;
    public static final int DELTA_RANGE = 19; //-9..9
    public static final int HASH_SPACE = DELTA_RANGE * DELTA_RANGE * DELTA_RANGE * DELTA_RANGE;
    static long[] score = new long[HASH_SPACE];
    public static void main(String[] args) {
        initials = AocInput.of(2024,22).stream().map(Long::parseLong).collect(Collectors.toList());
        System.out.println("Part 1: " + initials.stream().mapToLong(Day22::processSecretNumber).sum());
        System.out.println("Part 2: " + Arrays.stream(score).max().orElseThrow());
    }

    static long processSecretNumber(Long l) {
        boolean[] seen = new boolean[HASH_SPACE];
        int prevDigit = 0;
        int hash=0;
        int digit;
        for (int i=0; i<2000;i++) {
            l = nextSecret(l);
            digit = (int)(l % 10);
            hash = nextHash(hash,digit - prevDigit);
            if (i>3 && !seen[hash]) {
                seen[hash] = true;
                score[hash] += digit;
            }
            prevDigit = digit;
        }
        return l;
    }

    static long nextSecret(Long l) {
        l = l ^ (l<<6) & 16777215L;
        l = l ^ (l>>5) & 16777215L;
        l = l ^ (l<<11) & 16777215L;
        return l;
    }

    static int nextHash(int currentHash, int digit) {
        return ((currentHash * DELTA_RANGE) + (digit+9)) % (HASH_SPACE);
    }
}
