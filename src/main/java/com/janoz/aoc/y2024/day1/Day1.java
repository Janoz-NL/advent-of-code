package com.janoz.aoc.y2024.day1;

import java.util.ArrayList;
import java.util.List;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.collections.LongTuple;

public class Day1 {

    public static void main(String[] args) {
        read("inputs/2024/day01.txt");
        System.out.println(solve());
        System.out.println(solve2());
    }

    private static final List<Long> left = new ArrayList<>();
    private static final List<Long> right = new ArrayList<>();

    static void read(String file) {
        InputProcessor.asStream(file, LongTuple::new).forEach(t -> {
            left.add(t.getLeft());
            right.add(t.getRight());
        });
        left.sort((a,b) -> (int)(a-b));
        right.sort((a,b) -> (int)(a-b));
    }

    static long solve() {
        long diffs=0;
        for (int i=0; i<left.size(); i++) {
            diffs += Math.abs(left.get(i) - right.get(i));
        }
        return diffs;
    }

    static long solve2() {
        long similarity=0;
        for (Long leftVal : left) {
            for (Long rightVal : right) {
                if (leftVal.equals(rightVal)) {
                    similarity += leftVal;
                }
                if (leftVal.compareTo(rightVal) < 0) break;
            }
        }
        return similarity;
    }
}
