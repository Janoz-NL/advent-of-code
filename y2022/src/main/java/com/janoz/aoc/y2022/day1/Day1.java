package com.janoz.aoc.y2022.day1;

import com.janoz.aoc.InputProcessor;

public class Day1 {

    public static void main(String[] args) {
        int candidate = 0;
        int max = 0;
        int[] top3 = new int[3];

        for (String cals:new InputProcessor<>("inputs/2022/day01.txt", s->s)) {
            if ("".equals(cals)) {
                //part 1
                max = Math.max(candidate, max);

               //part 2
                store(top3, candidate);

                candidate = 0;
            } else {
                candidate += Integer.parseInt(cals);
            }
        }
        //part 1
        System.out.println(Math.max(candidate, max));

        //part 2
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
}
