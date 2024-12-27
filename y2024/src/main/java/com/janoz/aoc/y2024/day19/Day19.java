package com.janoz.aoc.y2024.day19;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.janoz.aoc.InputProcessor;

public class Day19 {


    static List<String> parts;

    public static void main(String[] args) {
        Iterator<String> input = InputProcessor.asIterator("inputs/2024/day19.txt");
        parts = Arrays.asList(input.next().split(", "));
        input.next();
        int count = 0;
        int total = 0;
        Map<String, Long> cache = new HashMap<>();
        while (input.hasNext()) {
            String towel = input.next();
            if (isPossible(towel)) {
                count++;
                total += countPossible(towel,cache);
            }
        }
        System.out.println("Part 1 : " + count);
        System.out.println("Part 2 : " + total);
    }

    static boolean isPossible(String towel) {
        if (towel.isEmpty()) return true;
        for (String part:parts) {
            if (towel.startsWith(part)) {
                if (isPossible(towel.substring(part.length()))) {
                    return true;
                }
            }
        }
        return false;
    }

    static long countPossible(String towel, Map<String, Long> cache) {
        if (cache.containsKey(towel)) {
            return cache.get(towel);
        }
        long count = 0L;
        for (String part:parts) {
            if (towel.equals(part)) {
                ++count;
            } else if (towel.startsWith(part)) {
                count += countPossible(towel.substring(part.length()),cache);
            }
        }
        cache.put(towel, count);
        return count;
    }

}
