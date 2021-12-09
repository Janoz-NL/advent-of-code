package com.janoz.aoc.geo;

import java.util.Objects;
import java.util.function.Function;

public class Point {

    public int x,y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

     public <T> void putOn(Grid<T> g,T v) {
         g.put(this,v);
    }

    public <T> T putOn(Grid<T> g, Function<T,T> f) {
        return g.put(this,f);
    }

    public Point north() {
        return new Point(x,y-1);
    }

    public Point south() {
        return new Point(x,y+1);
    }

    public Point east() {
        return new Point(x+1,y);
    }

    public Point west() {
        return new Point(x-1,y);
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


    public static Point parse(String input) {
        String[] parts = input.split(",");
        return new Point(Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim()));
    }

}
