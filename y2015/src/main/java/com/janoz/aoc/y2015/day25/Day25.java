package com.janoz.aoc.y2015.day25;

import com.janoz.aoc.input.AocInput;

public class Day25 {

    public static void main(String[] args) {
        solve(AocInput.of(2015,25));
    }

    static void solve(AocInput<String> input) {
        String[] parts = input.iterator().next().split("[ .,]");
        int col = Integer.parseInt(parts[21]);
        int row = Integer.parseInt(parts[18]);
        long times = getValue(col,row);
        long code = 20151125L;
        for (int i=1; i<times; i++) {
            code = step(code);
        }
        System.out.println("Part 1: " + code);
    }

    static long step(long input) {
        input *= 252533;
        return input % 33554393;
    }

    static long getValue(long col, long row) {
        return col + sum(col+row-2);
    }

    /*
     * 1   2   3   4   ... n-1 n
     * n   n-1 n-2 n-3     2   1
     * n+1 n+1 n+1 n+1 ... n+1 n+1 = n*(n+1)
     */
    static long sum(long n) {
        return ((n+1) * n) / 2;
    }
}
