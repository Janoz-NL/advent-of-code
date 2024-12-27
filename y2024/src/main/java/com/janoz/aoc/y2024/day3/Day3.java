package com.janoz.aoc.y2024.day3;

import java.util.stream.Collectors;

import com.janoz.aoc.InputProcessor;

public class Day3 {

    public static void main(String[] args) {
        String data = InputProcessor.asStream("inputs/2024/day03.txt").collect(Collectors.joining("#"));

        Day3 part1 = new Day3(data);
        while (part1.findNextMultiplication()) {}
        Day3 part2 = new Day3(data);
        while (part2.findNextMultiplicationWithConditional()) {}

        System.out.println(part1.result);
        System.out.println(part2.result);
    }

    Day3(String data) {
        this.data = data;
    }

    int curpos = 0;
    String data;
    long result = 0;

    boolean findNextMultiplication() {
        try {
            curpos = data.indexOf("mul(", curpos);
            return tryParseMultiplication();
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    boolean findNextMultiplicationWithConditional() {
        try {
            int dontPos = data.indexOf("don't()", curpos);
            if (dontPos < 0) dontPos = Integer.MAX_VALUE;
            curpos = data.indexOf("mul(", curpos);
            if (curpos > dontPos) {
                curpos = data.indexOf("do()", curpos);
                if (curpos < 0) return false;
                curpos = data.indexOf("mul(", curpos);
            }
            return tryParseMultiplication();
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    boolean tryParseMultiplication() {
        if (curpos == -1) return false;
        curpos += 4;
        long left = tryParseNumber();
        if (data.charAt(curpos) != ',') return true;
        curpos++;
        long right = tryParseNumber();
        if (data.charAt(curpos) != ')') return true;
        curpos++;
        result += left * right;
        return true;
    }

    int tryParseNumber() {
        int result = 0;
        while (Character.isDigit(data.charAt(curpos))) {
            result *= 10;
            result += (data.charAt(curpos) - '0');
            curpos++;
        }
        return result;
    }
}
