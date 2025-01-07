package com.janoz.aoc.y2015.day20;

import com.janoz.aoc.StopWatch;
import com.janoz.aoc.input.AocInput;

public class Day20 {

    public static void main(String[] args) {
        solve(AocInput.of(2015,20));
    }

    static void solve(AocInput<String> input) {
        Integer presents = input.addMapper(Integer::valueOf).iterator().next();
        StopWatch.start();
        int lower = findLower(presents);
        int bf1 = bruteforce(presents, lower);
        System.out.println("Part 1 :" + bf1);
        System.out.println("Part 2 :" + bruteforce2(presents,bf1));
        StopWatch.stopPrint();
    }


    static int findLower(int presents) {
        int i=1;
        int house = 1;
        while ((presents/10) > i) {
            house++;
            i = i * (house);
        }
        return i/(house);
    }

    static int bruteforce(int target, int lower) {
        int i=lower;
        while (presents(i) < target) {i++;}
        return i;
    }

    static int bruteforce2(int target, int lower) {
        int i=lower;
        while (presents2(i) < target) {i++;}
        return i;
    }

    static int presents(int house) {
        int presents = 0;
        for (int i = 1; i <= house; i++) {
            if (house % i ==0) presents += i*10;
        }
        return presents;
    }

    static int presents2(int house) {
        int presents = 0;
        for (int i = 1; i <= house; i++) {
            if (house % i == 0) {
                if (house / i <=50) presents += i*11;
            }
        }
        return presents;
    }
}
