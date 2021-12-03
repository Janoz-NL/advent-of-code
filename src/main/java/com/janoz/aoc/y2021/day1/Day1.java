package com.janoz.aoc.y2021.day1;

import com.janoz.aoc.InputIterable;

import java.util.Iterator;

public class Day1 {


    public static void main(String[] args) {
        System.out.println(
                new Day1().countIncreases(
                        new SlidingWindowIterator(
                                new InputIterable<>("inputs/day1.txt", Integer::valueOf).iterator())));
    }


    public Integer countIncreases(Iterator<Integer> input) {
        int previous = input.next();
        int result = 0;
        while (input.hasNext()) {
            int current = input.next();
            if (current > previous) {
                result ++;
            }
            previous = current;
        }
        return result;
    }

}
