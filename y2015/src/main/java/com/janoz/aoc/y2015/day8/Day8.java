package com.janoz.aoc.y2015.day8;

import com.janoz.aoc.input.AocInput;

import java.util.regex.Pattern;

public class Day8 {

    public static void main(String[] args) {
        solve(AocInput.of(2015,8));
    }

    static void solve(AocInput<String> input) {
        long[] answers = new long[2];
        input.stream().forEach(s -> {
            answers[0] += decodeDiff(s);
            answers[1] += encodeDiff(s);
        });
        System.out.println("Part 1: " + answers[0]);
        System.out.println("Part 2: " + answers[1]);
    }

    static Pattern hex = Pattern.compile("\\\\x[0-9a-f]{2}");
    static Pattern slash = Pattern.compile("\\\\[\\\\\"]");
    static Pattern escape = Pattern.compile("[\\\\\"]");

    static long decodeDiff(String input) {
        long hexCount = hex.matcher(input).results().count();
        long slashCount = slash.matcher(input).results().count();
        return 2 + hexCount * 3 + slashCount;
    }

    static long encodeDiff(String input) {
        return 2 + escape.matcher(input).results().count();
    }
}
