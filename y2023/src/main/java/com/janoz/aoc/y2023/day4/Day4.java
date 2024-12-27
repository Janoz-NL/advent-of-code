package com.janoz.aoc.y2023.day4;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.collections.Histogram;

public class Day4 {


    public static void main(String[] args) {
        System.out.println(solve2("inputs/2023/day04.txt"));
    }


    static int solve1(String file) {
        return InputProcessor.asStream(file).mapToInt(Day4::match).map(Day4::score).sum();
    }

    static long solve2(String file) {
        List<Integer> matches = InputProcessor.asStream(file).mapToInt(Day4::match).boxed().collect(Collectors.toList());
        Histogram<Integer, Long> cards = Histogram.longHistogram();
        for (int i=0; i< matches.size(); i++) {
            cards.inc(i);
            int match = matches.get(i);
            long amount = cards.get(i);
            for (int j=1; j<=match; j++) {
                cards.inc(i+j, amount);
            }
        }
        return cards.streamAmounts().mapToLong(Long::longValue).sum();
    }

    static int match(String s) {
        String[] numbers = s.split(":")[1].split("\\|");
        Set<String> s1 = new HashSet<>(Arrays.asList(numbers[0].split(" +")));
        Set<String> s2 = new HashSet<>(Arrays.asList(numbers[1].split(" +")));
        s1.remove("");
        s2.remove("");
        s1.retainAll(s2);
        return s1.size();
    }

    static int score(int amount) {
        return amount == 0? 0 : 1 << amount -1;
    }


}
