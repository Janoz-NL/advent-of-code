package com.janoz.aoc.y2015.day10;

import com.janoz.aoc.input.AocInput;

public class Day10 {

    public static void main(String[] args) {
        solve(AocInput.of(2015,10));
    }

    static void solve(AocInput<String> input) {
        String code = input.iterator().next();
        for (int i=0;i<40;i++) {
            code = next(code);
        }
        System.out.println("Part 1: " + code.length());
        for (int i=0;i<10;i++) {
            code = next(code);
        }
        System.out.println("Part 2: " + code.length());
    }

    static String next(String code) {
        StringBuilder sb = new StringBuilder();
        int count = 1;
        char previous = code.charAt(0);
        for (int i=1;i<code.length();i++) {
            if (previous == code.charAt(i)) {
                count++;
            } else {
                sb.append(count);
                sb.append(previous);
                previous = code.charAt(i);
                count = 1;
            }
        }
        sb.append(count);
        sb.append(previous);
        return sb.toString();
    }
}
