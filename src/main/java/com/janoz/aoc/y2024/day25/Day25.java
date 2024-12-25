package com.janoz.aoc.y2024.day25;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.janoz.aoc.InputProcessor;

public class Day25 {

    static List<int[]> keys = new ArrayList<>();
    static List<int[]> locks = new ArrayList<>();

    public static void main(String[] args) {
        Iterator<String> input = InputProcessor.asIterator("inputs/2024/day25.txt");
        while (input.hasNext()) readPattern(input);
        System.out.println(keys.stream()
                .mapToLong(k -> locks.stream()
                        .filter(l -> fit(k, l))
                        .count())
                .sum());
    }

    static boolean fit(int[] key, int[] lock) {
        for (int i=0;i<5;i++) {
            if (key[i] + lock[i] > 5) return false;
        }
        return true;
    }

    static void readPattern(Iterator<String> input) {
        String line = input.next();
        int[] pattern = new int[5];
        if ("#####".equals(line)) {
            for (int i=1; i<=5;i++) {
                line = input.next();
                for (int j=0; j<5; j++) {
                    if (line.charAt(j) == '#') pattern[j]=i;
                }
            }
            locks.add(pattern);
        } else {
            Arrays.fill(pattern,5);
            for (int i=4; i>=0;i--) {
                line = input.next();
                for (int j=0; j<5; j++) {
                    if (line.charAt(j) == '.') pattern[j]=i;
                }
            }
            keys.add(pattern);
        }
        input.next();
        if (input.hasNext()) input.next();
    }
}
