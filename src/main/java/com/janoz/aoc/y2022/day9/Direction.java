package com.janoz.aoc.y2022.day9;

import com.janoz.aoc.geo.Point;

import java.util.function.Function;

public enum Direction {
    UP (Point::north),
    DOWN (Point::south),
    LEFT (Point::west),
    RIGHT (Point::east);

    private Function<Point,Point> action;

    Direction(Function<Point, Point> action) {
        this.action = action;
    }

    public static Direction fromChar(char c) {
        switch (c) {
            case 'U':return UP;
            case 'D':return DOWN;
            case 'L':return LEFT;
            case 'R':return RIGHT;
            default: throw new RuntimeException("Wut??");
        }
    }

    public Point move(Point point) {
        return action.apply(point);
    }
}
