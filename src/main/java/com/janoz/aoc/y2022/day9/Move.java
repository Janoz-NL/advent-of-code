package com.janoz.aoc.y2022.day9;

public class Move {


    Direction dir;
    int steps;


    static Move parse(String input) {
        Move result = new Move();
        result.dir = Direction.fromChar(input.charAt(0));
        result.steps = Integer.parseInt(input.substring(2));
        return result;
    }


    @Override
    public String toString() {
        return "Move{" +
                "dir=" + dir +
                ", steps=" + steps +
                '}';
    }
}
