package com.janoz.aoc.y2023.day1;

import com.janoz.aoc.InputProcessor;

public class Day1 {

    public static void main(String[] args) {
        System.out.println(solve("inputs/2023/day01.txt"));
        System.out.println(solve2("inputs/2023/day01.txt"));
    }


    static int solve(String file) {
        return InputProcessor.asStream(file, Day1::getDigits).mapToInt(Integer::intValue).sum();
    }
    static int solve2(String file) {
        return InputProcessor.asStream(file, Day1::getDigits2).mapToInt(Integer::intValue).sum();
    }


    static int getDigits(String s) {
        StringBuilder sb = new StringBuilder(s);
        return getDigit(sb) * 10 + getDigit(sb.reverse());
    }

    static int getDigits2(String s) {
        return getDigit(replaceFirst(s)) * 10 + getDigit(replaceLast(s).reverse());
    }

    static int getDigit(StringBuilder s) {
        int result = 0;
        int i;
        i=0;
        while (!Character.isDigit(s.charAt(i))) i++;
        return s.charAt(i) - '0';
    }


    static StringBuilder replaceFirst(String in) {
        StringBuilder result = new StringBuilder(in);
        int i = 0;
        do {
            if (Character.isDigit(result.charAt(i))) return result;
            for (int j=1;j<10;j++) {
                if (result.substring(i).startsWith(numbers[j])) {
                    result.setCharAt(i, (char)(j + '0'));
                    return result;
                }
            }
            i++;
        } while (true);
    }

    static StringBuilder replaceLast(String in) {
        StringBuilder result = new StringBuilder(in);
        int i = in.length()-1;
        do {
            if (Character.isDigit(result.charAt(i))) return result;
            for (int j=1;j<10;j++) {
                if (result.substring(i).startsWith(numbers[j])) {
                    result.setCharAt(i, (char)(j + '0'));
                    return result;
                }
            }
            i--;
        } while (true);
    }

    static String[] numbers = {"","one","two","three","four","five","six","seven","eight","nine"};


}
