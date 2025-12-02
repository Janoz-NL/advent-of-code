package com.janoz.aoc.y2025.day2;

import com.janoz.aoc.Strings;
import com.janoz.aoc.collections.LongRange;
import com.janoz.aoc.collections.LongTuple;
import com.janoz.aoc.input.AocInput;

public class Day2 {

    public static void main(String[] args) {
        solve(AocInput.of(2025,2));
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

    static boolean isRepeating(int times, long i) {
        String s = String.valueOf(i);
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
