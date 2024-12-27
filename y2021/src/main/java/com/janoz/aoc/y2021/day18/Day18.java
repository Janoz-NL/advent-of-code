package com.janoz.aoc.y2021.day18;

import com.janoz.aoc.InputProcessor;

import java.util.List;
import java.util.stream.Collectors;

public class Day18 {


    public static void main(String[] args) {
        System.out.println(part1("inputs/2021/day18.txt").magnitude());
        System.out.println(part2("inputs/2021/day18.txt"));
    }

    static Pair part1(String input) {
        Pair result = new InputProcessor<>(input, Pair::parse).stream().reduce(null, Pair::add);
        System.out.println(result);
        return result;
    }

    static long part2(String input) {
        List<Pair> snailfishNumbers = new InputProcessor<>(input, Pair::parse).stream().collect(Collectors.toList());
        long max = 0;
        for (Pair a : snailfishNumbers) {
            for (Pair b : snailfishNumbers) {
                max = Math.max(max,Pair.add(a.clone(), b.clone()).magnitude());
            }
        }
        return max;
    }
}
