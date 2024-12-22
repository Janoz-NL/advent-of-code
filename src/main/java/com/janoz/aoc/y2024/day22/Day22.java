package com.janoz.aoc.y2024.day22;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.janoz.aoc.InputProcessor;

public class Day22 {

    static List<Long> initials;

    public static void main(String[] args) {
        initials = InputProcessor.asStream("inputs/2024/day22.txt",Long::parseLong).collect(Collectors.toList());
        System.out.println("Part 1: " + initials.stream().mapToLong(Day22::procesSecretNumber).sum());
        System.out.println("Part 2: " + sequenceScore.values().stream().mapToLong(l->l).max().orElseThrow());
    }

    static Map<Sequence, Long> sequenceScore = new HashMap<>();

    static long procesSecretNumber(Long l) {
        Set<Sequence> seen = new HashSet<>();
        Sequence s = new Sequence(0,0,0,0);
        long prevDigit = 0;
        long digit;
        for (int i=0; i<2000;i++) {
            l = nextSecret(l);
            digit = (l % 10);
            s = s.next(digit - prevDigit);
            if (i>3 && !seen.contains(s)) {
                seen.add(s);
                sequenceScore.merge(s, digit, Long::sum);
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

    private static class Sequence {
        int[] deltas;

        Sequence(int... deltas) {
            this.deltas = deltas;
        }

        Sequence next(long i) {
            int[] result = new int[4];
            System.arraycopy(deltas,1,result,0,3);
            result[3] = (int) i;
            return new Sequence(result);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Sequence sequence = (Sequence) o;
            return Arrays.equals(deltas, sequence.deltas);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(deltas);
        }
    }
}
