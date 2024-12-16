package com.janoz.aoc.geo;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Point {

    public static final Point ORIGIN = new Point(0,0);

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

    public Point fromOrigin(Point origin) {
        return translate(origin.x, origin.y);
    }

    public Point north() {
        return translate(Direction.NORTH);
    }

    public Point south() {
        return translate(Direction.SOUTH);
    }

    public Point east() {
        return translate(Direction.EAST);
    }

    public Point west() {
        return translate(Direction.WEST);
    }

    /**
     * Return all adjacent in counterclockwise order
     *
     * <pre>
     * 0 7 6
     * 1   5
     * 2 3 4
     * </pre>
     *
     * @return
     */
    public Point[] adjacent() {
        return new Point[]{
                translate(-1,-1),
                translate(-1,0),
                translate(-1,1),
                translate(0,1),
                translate(1,1),
                translate(1,0),
                translate(1,-1),
                translate(0,-1)
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

    public Collection<Point> neighbourCollection() {
        return Arrays.asList(neighbours());
    }

    public Stream<Point> streamNeighbour(Predicate<Point> filter) {
        return Arrays.stream(neighbours()).filter(filter);
    }

    public long manhattanDistance(Point p) {
        return Math.abs(x -p.x) + Math.abs(y - p.y);
    }

    public Point translate(int dx, int dy) { return new Point(x+dx,y+dy);}

    public Point translate(Point p) { return translate(p.x,p.y); }

    public boolean isTouching(Point p) {
        return Math.abs(x -p.x) <= 1 && Math.abs(y - p.y) <= 1;
    }

    public Direction directionTo(Point p) {
        return new Direction(p.x-this.x, p.y - this.y);
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
        return x * 521 + y;
    }


    private Integer randHash;
    /**
     *
     * @return more random hash which still adheres to hash rules
     */
    public int randHash() {
        if (randHash == null) {
            randHash = (int)new Random(y * 1000L + x).nextLong();
        }
        return randHash;
    }

    public static Point parse(String input) {
        String[] parts = input.split(",");
        return new Point(Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim()));
    }

    public static Predicate<Point> boundsPredicate(int width, int height) {
        return (p) -> p.x>=0 && p.y>=0 && p.x<width && p.y<height;
    }

    public static Predicate<Point> boundsPredicate(int minX, int maxX, int minY, int maxY) {
        return (p) -> p.x>=minX && p.y>=minY && p.x<=maxX && p.y<=maxY;
    }
}
