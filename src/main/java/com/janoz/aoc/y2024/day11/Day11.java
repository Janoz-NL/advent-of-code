package com.janoz.aoc.y2024.day11;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.janoz.aoc.StopWatch;
import com.janoz.aoc.collections.AccumulatingMap;

public class Day11 {
    static Map<Long, Long> stones;

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
        Arrays.stream(input.split(" "))
                .mapToLong(Long::parseLong)
                .forEach(l -> stones.merge(l,1L, Long::sum));
    }

    static void blink(int times) {
        for (int i=0; i< times; i++) {
            blink();
        }
    }

    static void blink() {
        final AccumulatingMap<Long,Long,Long> next =
                new AccumulatingMap<>((newEngraving, engraving)-> stones.get(engraving), Long::sum);
        stones.keySet().forEach(engraving -> {
                    int digits;
                    if (engraving == 0) {
                        next.accumulate(1L, engraving);
                    } else if (((digits = (int) (Math.log10(engraving) + 1)) & 1) == 0) {
                        long split = (long) Math.pow(10, digits>>1);
                        next.accumulate(engraving / split, engraving);
                        next.accumulate(engraving % split, engraving);
                    } else {
                        next.accumulate(engraving * 2024, engraving);
                    }
        });
        stones = next;
    }

    static long count() {
        return stones.values().stream().mapToLong(Long::longValue).sum();
    }
}