package com.janoz.aoc.y2025.day3;

import com.janoz.aoc.StopWatch;
import com.janoz.aoc.input.AocInput;

import java.util.List;

public class Day3 {

    public static void main(String[] args) {
        solve(AocInput.of(2025,3));
    }

    static void solve(AocInput<String> input) {
        List<String> list = input.asList();
        //warmup
        list
                .stream()
                .map(s -> findBiggest(0, s.length() - 15, s).toString())
                .mapToLong(Long::parseLong)
                .sum();


        StopWatch.start();
        long part1 = list
                .stream()
                .map(s -> findBiggest(0, s.length() - 1, s).toString())
                .mapToLong(Long::parseLong)
                .sum();
        StopWatch.stopPrint();
        System.out.printf("Part 1 : %d\n",part1);

        StopWatch.start();
        long part2 = list
                .stream()
                .map(s -> findBiggest(0, s.length() - 11, s).toString())
                .mapToLong(Long::parseLong)
                .sum();
        StopWatch.stopPrint();
        System.out.printf("Part 2 : %d\n", part2);
    }

    public static StringBuilder findBiggest(int start, int end, String input) {
        int max = -1;
        int pos = -1;
        for (int i=start; i<end; i++) {
            if (input.charAt(i) > max) {
                pos = i;
                max = input.charAt(i);
            }
        }
        if (end == input.length()) return new StringBuilder("" + (max - '0'));
        else return findBiggest(pos+1, end+1, input).insert(0, max - '0');
    }
}
