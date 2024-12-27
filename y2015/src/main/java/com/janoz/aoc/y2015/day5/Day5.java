package com.janoz.aoc.y2015.day5;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.janoz.aoc.InputProcessor;

public class Day5 {
    public static void main(String[] args) {
        System.out.println("Part 1 :" + InputProcessor.asStream("inputs/2015/day5.txt").filter(Day5::validatePart1).count());
        System.out.println("Part 1 :" + InputProcessor.asStream("inputs/2015/day5.txt").filter(Day5::validatePart2).count());
    }
    static final Set<Character> vowelSet = new HashSet<>(Arrays.asList('a','e','i','o','u'));
    static final Set<String> naughtys = new HashSet<>(Arrays.asList("ab","cd","pq","xy"));

    static boolean validatePart1(String word) {
        int vowels = vowelSet.contains(word.charAt(0))?1:0;
        boolean hasDouble = false;
        for (int i=1; i<word.length(); i++) {
            if (vowelSet.contains(word.charAt(i))) vowels++;
            String part = word.substring(i-1, i+1);
            if (part.charAt(0) == part.charAt(1)) hasDouble = true;
            if (naughtys.contains(part)) return false;
        }
        return hasDouble && (vowels >= 3);
    }

    static boolean validatePart2(String word) {
        boolean contains2Pair = false;
        for (int i=0;i<word.length()-3;i++) {
            if (word.substring(i + 2).contains(word.substring(i, i + 2))) {
                contains2Pair = true;
                break;
            }
        }
        boolean containsMirrorPair = false;
        for (int i=0;i<word.length()-2;i++) {
            if (word.charAt(i) == word.charAt(i+2)) {
                containsMirrorPair = true;
                break;
            }
        }
        return contains2Pair && containsMirrorPair;
    }
}
