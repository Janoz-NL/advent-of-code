package com.janoz.aoc.y2021.day2;

import com.janoz.aoc.InputIterable;

public class Day2 {

    public static void main(String[] args) {
        System.out.println(
                new Day2().determinePosition2(
                                new InputIterable<>("inputs/day2.txt", Movement::new)));
    }

    public int determinePosition1(Iterable<Movement> input) {
        int hor = 0;
        int depth = 0;
        for(Movement m: input) {
            switch (m.direction) {
                case FORWARD:
                    hor += m.length;
                    break;
                case UP:
                    depth -= m.length;
                    break;
                case DOWN:
                    depth += m.length;
            }
        }
        return hor * depth;
    }

    public int determinePosition2(Iterable<Movement> input) {
        int hor = 0;
        int aim = 0;
        int depth = 0;
        for(Movement m: input) {
            switch (m.direction) {
                case FORWARD:
                    hor += m.length;
                    depth += aim * m.length;
                    break;
                case UP:
                    aim -= m.length;
                    break;
                case DOWN:
                    aim += m.length;

            }
        }
        return hor * depth;
    }

}
