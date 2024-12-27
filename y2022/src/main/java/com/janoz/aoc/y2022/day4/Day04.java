package com.janoz.aoc.y2022.day4;

import com.janoz.aoc.InputProcessor;

public class Day04 {

    public static void main(String[] args) {
        String file = "inputs/2022/day04.txt";
        System.out.println(part1(file));
        System.out.println(part2(file));
    }

    static long part1(String file) {
        return InputProcessor.asStream(file, Day04::parse).filter(r -> r[0].contains(r[1]) || r[1].contains(r[0])).count();
    }

    static long part2(String file) {
        return InputProcessor.asStream(file, Day04::parse).filter(r -> r[0].overlaps(r[1]) || r[1].overlaps(r[0])).count();
    }

    static Range[] parse(String input) {
        Range[] result = new Range[2];
        String[] parts = input.split(",");
        result[0] = new Range(parts[0]);
        result[1] = new Range(parts[1]);
        return result;
    }

    static class Range {
        int start;
        int end;

        Range(String input) {
            String[] parts = input.split("-");
            start = Integer.parseInt(parts[0]);
            end = Integer.parseInt(parts[1]);
        }

        boolean contains(Range other) {
            return (start <= other.start && end >= other.end);
        }

        boolean overlaps(Range other) {
            return !(end < other.start || other.end < start);
        }
    }
}
