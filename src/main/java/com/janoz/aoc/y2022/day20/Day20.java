package com.janoz.aoc.y2022.day20;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Day20 {

    public static void main(String[] args) throws Exception {
        String file = "inputs/2022/day20.txt";
        List<Long> numbers = InputProcessor.asStream(file, Long::parseLong).collect(Collectors.toList());

        StopWatch.start();
        long part1 = decrypt(numbers, 1, 1);
        StopWatch.stopPrint();
        System.out.println(part1);

        StopWatch.start();
        long part2 = decrypt(numbers, 10, 811589153);
        StopWatch.stopPrint();
        System.out.println(part2);
    }

    private static long decrypt(List<Long> encrypted, int rounds, long encryptionKey) {
        List<LongContainer> original = encrypted.stream().map(l -> new LongContainer(l * encryptionKey)).collect(Collectors.toList());
        List<LongContainer> shuffled = new ArrayList<>(original);

        int currentPos, newPos, direction, distance;
        long delta;

        for(int i = 0; i < rounds; i++) {
            for(LongContainer current : original) {
                currentPos = shuffled.indexOf(current);
                shuffled.remove(currentPos);
                delta = current.val + currentPos;
                direction = (int)Math.signum(delta);
                distance = (int)(Math.abs(delta) % shuffled.size());

                newPos = distance * direction;
                if (newPos <= 0) newPos += shuffled.size();
                shuffled.add(newPos, current);
            }
        }

        int fromIndex = shuffled.indexOf(LongContainer.origin);
        return IntStream.rangeClosed(1,3)
                .map(i -> i * 1000)
                .map(i -> i + fromIndex)
                .map(i -> i % shuffled.size())
                .mapToLong(i -> shuffled.get(i).val)
                .sum();
    }

    /**
     * Container so the same long values are treated as different objects
     */
    private static class LongContainer {
        static LongContainer origin;
        long val;

        public LongContainer(long val) {
            if (val == 0) origin = this;
            this.val = val;
        }
    }
}
