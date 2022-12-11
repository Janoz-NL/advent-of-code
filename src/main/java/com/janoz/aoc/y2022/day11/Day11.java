package com.janoz.aoc.y2022.day11;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.StopWatch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.LongFunction;

public class Day11 {

    public static final LongFunction<Long> PART_1 = l -> l / 3;
    public static final LongFunction<Long> PART_2 = l -> l % (2L * 3 * 5 * 7 * 11 * 13 * 17 * 19 * 23);

    public static void main(String[] args) {
        String file = "inputs/2022/day11.txt";

        StopWatch.start();
        System.out.println(solve(file, 20, PART_1));
        StopWatch.stopPrint();

        StopWatch.start();
        System.out.println(solve(file, 10000, PART_2));
        StopWatch.stopPrint();
    }

    static long solve(String file, int iterations, LongFunction<Long> worryModifier) {
        List<Monkey> monkeys = parseInput(file);
        monkeys.forEach(m -> m.postParse(monkeys,worryModifier));
        for (int i=0; i<iterations; i++) {
            monkeys.forEach(Monkey::doInspections);
        }
        long[] inspections = monkeys.stream().mapToLong(Monkey::getItemsInspected).sorted().toArray();
        return inspections[inspections.length-1] * inspections[inspections.length-2];
    }

    static List<Monkey> parseInput(String file) {
        List<Monkey> monkeys = new ArrayList<>();
        Iterator<String> it = InputProcessor.asIterator(file);
        while (it.hasNext()) {
            monkeys.add(Monkey.parse(it));
        }
        return monkeys;
    }
}
