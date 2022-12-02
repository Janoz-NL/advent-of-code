package com.janoz.aoc.y2022.day1;

import com.janoz.aoc.InputProcessor;

public class Day1 {

    public static void main(String[] args) {
        int candidate = 0;
        int max = 0;
        int[] top3 = new int[3];

        for (int cals:new InputProcessor<>("inputs/2022/day01.txt", Day1::parseIntSave)) {
            if (cals < 0) {
                //P1
                max = Math.max(candidate, max);

               //P2
                store(top3, candidate);

                candidate = 0;
            } else {
                candidate += cals;
            }
        }
        //P1
        System.out.println(Math.max(candidate, max));

        //P2
        store(top3, candidate);
        System.out.println(top3[0] + top3[1] + top3[2]);
    }

    static void store(int[] top, int candidate) {
        if (candidate < top[2]) return;
        int i = 2;
        while (i > 0 && top[i-1] <= candidate) {
            top[i] = top[i-1];
            i--;
        }
        top[i] = candidate;
    }

    static int parseIntSave(String s) {
        if ("".equals(s)) return -1;
        return Integer.parseInt(s);
    }
}
