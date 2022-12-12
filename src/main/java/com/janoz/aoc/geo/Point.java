package com.janoz.aoc.geo;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class Point {

    public int x,y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public <T> T putOn(Grid<T> g,T v) {
         g.put(this,v);
         return v;
    }

    public <T> T putOn(Grid<T> g, Function<T,T> f) {
        return g.put(this,f);
    }

    public Point north() {
        return translate(0,-1);
    }

    public Point south() {
        return translate(0,+1);
    }

    public Point east() {
        return translate(+1,0);
    }

    public Point west() {
        return translate(-1,0);
    }

    public Point[] adjacent() {
        return new Point[]{
                translate(-1,-1),
                translate(-1,0),
                translate(-1,1),
                translate(0,-1),
                translate(0,1),
                translate(1,-1),
                translate(1,0),
                translate(1,1)
        };
    }

    /**
     * @return Only straight adjacent, not diagonally
     */
    public Point[] neighbours() {
        return new Point[]{
                translate(-1,0),
                translate(0,-1),
                translate(0,1),
                translate(1,0),
        };
    }

    public long manhattanDistance(Point p) {
        return Math.abs(x -p.x) + Math.abs(y - p.y);
    }

    public Point translate(int dx, int dy) { return new Point(x+dx,y+dy);}

    public boolean isTouching(Point p) {
        return Math.abs(x -p.x) <= 1 && Math.abs(y - p.y) <= 1;
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

    public static Predicate<Point> boundsPredicate(int width, int height) {
        return (p) -> p.x>=0 && p.y>=0 && p.x<width && p.y<height;
    }
}
