package com.janoz.aoc.y2025.day1;

import com.janoz.aoc.InputProcessor;

public class Day1 {

    public static void main(String[] args) {
        solve("inputs/2025/day01.txt");
    }

    static void solve(String file) {
        long at0 = 0;
        long pass0 = 0;
        int position = 50;
        for (int amount : InputProcessor.asIterable(file, Day1::parse)) {
            if (amount == 0) continue;
            pass0 += Math.abs(amount/100);
            amount = amount % 100;

            int newPosition = position + amount;


            if (newPosition < 0) {
                newPosition += 100;
                if (position !=0 && newPosition != 0) pass0++;
            }
            if (newPosition >= 100) {
                newPosition -= 100;
                if (position !=0 && newPosition != 0) pass0++;
            }
            if (newPosition == 0) at0++;
            position = newPosition;
        }
        System.out.printf("Landings : %d%n", at0);
        System.out.printf("Ticks    : %d%n", at0 + pass0);
    }

    static int parse(String s) {
        int amount = Integer.parseInt(s.substring(1));
        if (s.charAt(0) == 'L') return -amount;
        return amount;
    }
}
