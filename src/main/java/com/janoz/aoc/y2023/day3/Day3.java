package com.janoz.aoc.y2023.day3;

import java.util.Iterator;
import java.util.function.BiPredicate;

import com.janoz.aoc.InputProcessor;

public class Day3 {

    public static void main(String[] args) {
        System.out.println(solve("inputs/2023/day03.txt"));
    }


    static int solve(String file) {
        Iterator<String> i = InputProcessor.asIterator(file);
        int result = 0;
        String r1 = "";
        String r2 = "";
        String r3 = i.next();
        while (i.hasNext()) {
            r1 = r2;
            r2 = r3;
            r3 = i.next();
            result += sum(r1, r2, r3);
        }
        return result + sum(r2,r3,"");
    }


    private static int sum(String r1, String r2, String r3) {
        int current = 0;
        int total = 0;
        boolean valid = false;
        boolean gear = false;
        for (int i=0; i<=r2.length(); i++) {
            if (isDigit(i,r2)) {
                current *= 10;
                current += r2.charAt(i) - '0';
                valid = valid || isAround(i,r1,r2,r3, Day3::isSymbol);
                gear = gear || isAround(i,r1,r2,r3, Day3::isGear);
            } else if (current != 0) {
                if (valid) {
                    total += current;
                }
                if (gear) {

                }
                current = 0;
                valid = false;
                gear = false;
            }
        }

        return total;
    }

    private static boolean isAround(int index, String r1, String r2, String r3, BiPredicate<Integer, String> test) {
        return
                test.test(index-1, r1) || test.test(index, r1) || test.test(index + 1, r1) ||
                test.test(index-1, r2) || test.test(index + 1, r2) ||
                test.test(index-1, r3) || test.test(index, r3) || test.test(index + 1, r3);
    }


    private static boolean isSymbol(int i, String r) {
        return !(i < 0 || i >= r.length() || r.charAt(i) == '.' || Character.isDigit(r.charAt(i)));
    }

    private static boolean isDigit(int i,String r) {
        return i >= 0 && i < r.length() && Character.isDigit(r.charAt(i));
    }

    private static boolean isGear(int i,String r) {
        return i >= 0 && i < r.length() && r.charAt(i) == '*';
    }
}
