package com.janoz.aoc.y2022.day3;

import com.janoz.aoc.InputProcessor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;

public class Day3 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        String file = "inputs/2022/day03.txt";
        System.out.println(part1(file));
        System.out.println(part2(file));
    }

    static long part1(String file) {
        return InputProcessor.asStream(file)
                .mapToInt(s -> toPriority(findItem(s)))
                .sum();
    }

    static long part2(String file) {
        Iterator<String> iterator = InputProcessor.asIterator(file);
        long sum = 0;
        while (iterator.hasNext()) {
            sum += toPriority(findBadge(iterator));
        }
        return sum;
    }

    static char findBadge(Iterator<String> iterator) {
        char[] s1 = iterator.next().toCharArray();
        String s2 = iterator.next();
        String s3 = iterator.next();
        for (char c:s1) {
            if ((s2.indexOf(c) >=0) && (s3.indexOf(c) >=0)) {
                return c;
            }
        }
        throw new RuntimeException("nothing");
    }

    static char findItem(String s) {
        int half = s.length() / 2;
        char[] s1 = s.substring(0,half).toCharArray();
        String s2 = s.substring(half);
        for (char c:s1) {
            if (s2.indexOf(c) >=0) {
                return c;
            }
        }
        throw new RuntimeException("nothing");
    }

    static int toPriority(char c) {
        if (c >= 'a') {
            return c - 'a' + 1;
        }
        return c - 'A' + 27;
    }
}
