package com.janoz.aoc.y2022.day10;

import com.janoz.aoc.InputProcessor;

import java.io.IOException;

public class Day10 {

    public static void main(String[] args) throws IOException {
        String file = "inputs/2022/day10.txt";
        InputProcessor.asStream(file).forEach(Day10::processStep);
        System.out.println(sum);
        for (int i=0; i<240; i++) {
            if (i % 40 == 0) System.out.println();
            System.out.print(screen[i]?"##":"  ");
        }
    }

    static int cycle = 0;
    static long x = 1;
    static long sum = 0;
    static boolean [] screen = new boolean[240];

    static void processStep(String s) {
        tick(s);
        if (s.startsWith("addx")) {
            tick(s);
            x += Integer.parseInt(s.substring(5));
        }
    }

    static void tick(String s) {
        //part 2
        int screenpos = cycle % 40;
        if (screenpos-1 <= x && x <= screenpos+1) {
            screen[cycle] = true;
        }

        cycle++;

        //part 1
        if (((cycle-20) % 40) == 0) {
            sum += cycle * x;
        }
    }
}
