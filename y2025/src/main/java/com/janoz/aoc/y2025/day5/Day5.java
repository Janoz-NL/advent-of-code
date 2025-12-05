package com.janoz.aoc.y2025.day5;

import com.janoz.aoc.StopWatch;
import com.janoz.aoc.collections.LongRange;
import com.janoz.aoc.collections.LongTuple;
import com.janoz.aoc.input.AocInput;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Day5 {

    public static void main(String[] args) {
        StopWatch.start();
        LongTuple result = solve(AocInput.of(2025,5));
        StopWatch.stopPrint();
        printResult(result);
    }

    static LongTuple solve(AocInput<String> input) {
        Set<LongRange> combined = new HashSet<>();
        for (LongRange r : input.addMapper(Day5::parse).iterable()) {
            addRange(combined,r);
        }

        long part1  = input.stream()
                .map(Long::parseLong)
                .filter(l -> combined.stream().anyMatch(r -> r.contains(l)))
                .count();
        long part2 = combined.stream().mapToLong(LongRange::size).sum();
        return new LongTuple(part1,part2);
    }

    static LongRange parse(String s) {
        return new LongRange(Arrays.stream(s.split("-")).mapToLong(Long::parseLong).toArray());
    }


    static void addRange(Set<LongRange> ranges, LongRange range) {
        Set<LongRange> touches = ranges.stream().filter(range::touches).collect(Collectors.toSet());
        ranges.removeAll(touches);
        LongRange combined = touches.stream().reduce(range, Day5::combine);
        ranges.add(combined);
    }

    private static LongRange combine(LongRange l1, LongRange l2) {
        //assume they touch
        return new LongRange(l1.getMin(), l1.getMax(), l2.getMin(),l2.getMax());
    }

    static void printResult(LongTuple results) {
        System.out.printf("Part 1 : %d\nPart 2 : %d",results.getLeft(), results.getRight());
    }
}
