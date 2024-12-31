package com.janoz.aoc.y2015.day17;

import com.janoz.aoc.input.AocInput;

import java.util.List;

public class Day17 {
    public static void main(String[] args) {
        solve(AocInput.of(2015,17));
    }

    static void solve(AocInput<String> input) {
        List<Integer> jugs = input.addMapper(Integer::parseInt).asList();
        System.out.println("Part 1 :" + fits(jugs,0,0, jugs.size()));
        int min = min(jugs,0,0,0);
        System.out.println("Part 2 :" + fits(jugs,0,0, min));
    }

    private static int fits(List<Integer> jugs, int index, int amount, int maxContainers) {
        if (amount > 150) return 0;
        if (amount == 150) return 1;
        if (index == jugs.size()) return 0;
        if (maxContainers == 0) return 0;
        return
                fits(jugs, index+1, amount, maxContainers) +
                fits(jugs, index+1, amount + jugs.get(index), maxContainers-1);
    }

    private static int min(List<Integer> jugs, int index, int amount, int jugAmount) {
        if (amount > 150) return Integer.MAX_VALUE;
        if (amount == 150) return jugAmount;
        if (index == jugs.size()) return Integer.MAX_VALUE;
        return Math.min(
                min(jugs, index+1, amount, jugAmount),
                min(jugs, index+1, amount + jugs.get(index), jugAmount+1));
    }
}
