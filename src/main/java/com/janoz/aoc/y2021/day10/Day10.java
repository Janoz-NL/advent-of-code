package com.janoz.aoc.y2021.day10;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.collections.SortedBag;

import java.util.Stack;

public class Day10 {

    public static void main(String[] args) {
        Day10 d = new Day10();
        System.out.println(d.process("inputs/2021/day10.txt"));
        System.out.println(d.getPart2Score());
    }

    static String open="([{<";
    static String close=")]}>";
    static int[] scoreMap = new int[]{3, 57,1197, 25137};

    Stack<Character> parseStack = new Stack<>();
    SortedBag<Long,Long> part2Score = SortedBag.longSortedBag();

    long process(String input) {
        return new InputProcessor<>(input, this::score).stream().mapToLong(Long::longValue).sum();
    }

    long score(String line) {
        parseStack.clear();
        for (char c: line.toCharArray()) {
            if (open.indexOf(c) >=0) {
                parseStack.push(map(c));
            } else {
                if (c != parseStack.pop()) {
                     return score1(c);
                }
            }
        }
        part2Score.put(scoreStack());
        return 0;
    }

    long getPart2Score() {
        return part2Score.med();
    }

    long scoreStack() {
        long score = 0;
        while (!parseStack.isEmpty()) {
            score *= 5;
            score += score2(parseStack.pop());
        }
        return score;
    }

    long score2(char c) {
        return close.indexOf(c) + 1;
    }

    long score1(char c) {
        return scoreMap[close.indexOf(c)];
    }

    char map(char c) {
        return c=='('?')':(char)(c+2);
    }
}
