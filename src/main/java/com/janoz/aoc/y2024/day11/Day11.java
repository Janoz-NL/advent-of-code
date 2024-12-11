package com.janoz.aoc.y2024.day11;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.janoz.aoc.StopWatch;

public class Day11 {
    static Map<Long, Long> stones;
    static Map<Long, Long> next;

    public static void main(String[] args) {
        StopWatch.start();
        parse("5 62914 65 972 0 805922 6521 1639064");
        blink(25);
        System.out.println(count());
        blink(50);
        System.out.println(count());
        StopWatch.stopPrint();
    }

    static void parse(String input) {
        stones = new HashMap<>();
        next = new HashMap<>();
        Arrays.stream(input.split(" "))
                .mapToLong(Long::parseLong)
                .forEach(eng -> stones.put(eng, stones.getOrDefault(eng,0L) + 1));
    }

    static void blink(int times) {
        for (int i=0; i< times; i++) {
            blink();
        }
    }

    static void blink() {
        int digits;
        for (long engraving: stones.keySet()) {
            if (engraving == 0) {
                putNext(1L,engraving);
            } else if ((digits = (int) (Math.log10(engraving) + 1)) % 2 == 0) {
                long split = (long) Math.pow(10, digits>>1);
                long engraving1 = engraving / split;
                long engraving2 = engraving % split;
                putNext(engraving1,engraving);
                putNext(engraving2,engraving);
            } else {
                putNext(engraving * 2024, engraving);
            }
        }
        stones = Map.copyOf(next);
        next.clear();
    }

    static void putNext(long newEngraving, long previousEngraving) {
        next.put(newEngraving, next.getOrDefault(newEngraving,0L) + stones.get(previousEngraving));
    }

    static long count() {
        return stones.values().stream().mapToLong(Long::longValue).sum();
    }
}
