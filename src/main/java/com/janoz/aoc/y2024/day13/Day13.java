package com.janoz.aoc.y2024.day13;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.StopWatch;
import com.janoz.aoc.geo.Direction;

import java.util.Iterator;

public class Day13 {

    public static void main(String[] args) {
        Iterator<String> input = InputProcessor.asIterator("inputs/2024/day13.txt");
        long score1 = 0;
        long score2 = 0;
        StopWatch.start();
        while(input.hasNext()){
            Direction[] machine = parse(input);
            score1 += solveClaw(machine,0);
            score2 += solveClaw(machine,10000000000000L);
        }
        StopWatch.stopPrint();
        System.out.println("Part 1: " + score1);
        System.out.println("Part 2: " + score2);
    }

    static Direction[] parse(Iterator<String> input) {
        Direction dirA = parseDirection(input.next());
        Direction dirB = parseDirection(input.next());
        Direction prize = parsePrize(input.next());
        if (input.hasNext()) input.next();
        return new Direction[]{dirA, dirB, prize};
    }

    static long solveClaw(Direction[] input, long correction) {
        return algebraSolve(input[0], input[1], input[2].x + correction, input[2].y + correction);
    }

    private static int simpleSolve(Direction dirA, Direction dirB, Direction prize) {
        for (int a=1; a<=100; a++) {
            Direction delta = dirA.times(a).directionTo(prize);
            if (delta.x % dirB.x == 0) {
                int b = delta.x / dirB.x;
                if (dirB.y * b == delta.y) return 3 * a + b;
            }
        }
        return 0;
    }

    public static long algebraSolve(Direction dirA, Direction dirB, long px, long py) {
        long top = dirA.x * py - dirA.y * px;
        long bottom = (long)dirA.x * dirB.y - (long)dirB.x * dirA.y;
        if (top % bottom == 0) {
            long b = top / bottom;
            top = py- b* dirB.y;
            if (top % dirA.y == 0) {
                long a = top / dirA.y;
                return 3*a + b;
            }
        }
        return 0;
    }

    static Direction parseDirection(String input) {
        return new Direction(Integer.parseInt(input.substring(12,14)),Integer.parseInt(input.substring(18,20)));
    }

    static Direction parsePrize(String input) {
        String[] parts = input.split(",");
        return new Direction(Integer.parseInt(parts[0].substring(parts[0].indexOf('=')+1)),Integer.parseInt(parts[1].substring(parts[1].indexOf('=')+1)));
    }
}
