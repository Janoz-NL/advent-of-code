package com.janoz.aoc.y2022.day2;

import com.janoz.aoc.InputProcessor;

// 13695


public class Day2 {

    public static void main(String[] args) {
        System.out.println(
            new InputProcessor<>("inputs/2022/day02.txt", Day2::mapToRPS).stream().mapToInt(Day2::score).sum()
        );
        System.out.println(
                new InputProcessor<>("inputs/2022/day02.txt", Day2::mapToOutcome).stream().mapToInt(Day2::score).sum()
        );
    }

    static RPS[] mapToOutcome(String input) {
        RPS[] result = new RPS[2];
        result[0] = RPS.fromChar(input.charAt(0));
        result[1] = result[0].withOutcome(input.charAt(2));
        return result;
    }

    static RPS[] mapToRPS(String input) {
        RPS[] result = new RPS[2];
        result[0] = RPS.fromChar(input.charAt(0));
        result[1] = RPS.fromChar(input.charAt(2));
        return result;
    }

    static int score(RPS[] inputs) {
        return score(inputs[0],inputs[1]);
    }

    static int score(RPS other, RPS you) {
        int score = you.ordinal()+1;
        int outcome = you.battle(other);
        if (outcome == 0) {
            score += 3;
        } else if (outcome > 0) {
            score += 6;
        }
        return score;
    }
}
