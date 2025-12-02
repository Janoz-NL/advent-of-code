package com.janoz.aoc.y2025.day2;

import com.janoz.aoc.StopWatch;
import com.janoz.aoc.Strings;
import com.janoz.aoc.collections.LongRange;
import com.janoz.aoc.collections.LongTuple;
import com.janoz.aoc.input.AocInput;

public class Day2 {

    public static void main(String[] args) {
//        StopWatch.start();
//        solve(AocInput.of(2025,2));
//        StopWatch.stopPrint();


        StopWatch.start();
        long part1 = solveParalel1(AocInput.of(2025, 2));
        StopWatch.stopPrint();
        System.out.println(part1);
        StopWatch.start();
        long part2 = solveParalel2(AocInput.of(2025, 2));
        StopWatch.stopPrint();
        System.out.println(part2);

    }

    public static void solve(AocInput<String> input) {
        Iterable<String> pairs = Strings.streamCSV(input.asString(s->s))::iterator;
        long sum1 = 0;
        long sum2 = 0;
        for(String pair : pairs) {
            LongRange l = new LongTuple(pair,"-").getRange();
            for (Long j :  l.iterable()) {
                if (isRepeating(2, j)) {
                    sum1 += j;
                }
                for (int k=2; k<10; k++) if (isRepeating(k, j)) {
                    sum2 += j;
                    break;
                }
            }
        }
        System.out.println(sum1);
        System.out.println(sum2);
    }

    public static long solveParalel1(AocInput<String> input) {
        return Strings.streamCSV(input.asString(s->s))
                .map(s -> new LongTuple(s,"-").getRange())
                .flatMapToLong(LongRange::stream)
                .parallel()
                .filter(l -> isRepeating(2, l))
                .sum();
    }

    public static long solveParalel2(AocInput<String> input) {
        return Strings.streamCSV(input.asString(s->s))
                .map(s -> new LongTuple(s,"-").getRange())
                .flatMapToLong(LongRange::stream)
                .parallel()
                .filter(l -> {
                    String s = String.valueOf(l);
                    for (int k=s.length(); k>1; k--)
                        if (isRepeating(k, l)) {
                        return true;
                    }
                    return false;
                })
                .sum();
    }

    static boolean isRepeating(int times, long i) {
        String s = String.valueOf(i);
        return isRepeating(times, s);
    }

    static boolean isRepeating(int times, String s) {
        if (s.length() % times != 0) return false;
        int partLength = s.length() / times;
        for (int j = 0; j < partLength; j++) {
            char c = s.charAt(j);
            for (int k=1; k<times; k++) {
                if (s.charAt(j + (k * partLength)) != c) return false;
            }
        }
        return true;
    }
}
