package com.janoz.aoc.y2021;

import java.io.IOException;
import java.util.Iterator;

public class Day1 {


    public static void main(String[] args) throws IOException {
        System.out.println(
                new Day1().countIncreases(
                        new SlidingWindowIterator(
                                new InputIterator("inputs/day1.txt"))));
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
