package com.janoz.aoc.y2021.day2;

import java.util.Locale;

public class Movement {

    int length;
    Direction direction;


    public Movement(String input) {
        String[] parts = input.split(" ");
        length = Integer.parseInt(parts[1]);
        direction = Direction.valueOf(parts[0].toUpperCase(Locale.ROOT));
    }

    public enum Direction {
        FORWARD,
        DOWN,
        UP
    }

}
