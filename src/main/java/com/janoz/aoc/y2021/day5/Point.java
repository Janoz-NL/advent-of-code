package com.janoz.aoc.y2021.day5;

import java.util.Objects;

public class Point {

    public int x,y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Point parse(String input) {
        String[] parts = input.split(",");
        return new Point(Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim()));
    }

    @Override
    public String toString() {
        return x + "," + y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
