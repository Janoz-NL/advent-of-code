package com.janoz.aoc.geo;

import java.util.Objects;
import java.util.stream.Stream;

/**
 *
 *    z
 *     |
 *     |
 *     |_____ y
 *    /
 *   /
 *  x
 *
 */
public class Point3D {

    public static final Point3D ORIGIN = new Point3D(0,0,0);

    public final long x;
    public final long y;
    public final long z;

    public Point3D(long x, long y, long z) {
        this.x=x;
        this.y=y;
        this.z=z;
    }

    public boolean isAbove(Point3D other) {
        return z > other.z;
    }

    public boolean isBelow(Point3D other) {
        return z < other.z;
    }

    public boolean isBefore(Point3D other) {
        return x > other.x;
    }

    public boolean isAfter(Point3D other) {
        return x < other.x;
    }

    public boolean isRight(Point3D other) {
        return y > other.y;
    }

    public boolean isLeft(Point3D other) {
        return y < other.y;
    }

    public Point3D above() {
        return new Point3D(x,y,z+1);
    }

    public Point3D below() {
        return new Point3D(x,y,z-1);
    }

    public Point3D before() {
        return new Point3D(x+1,y,z);
    }

    public Point3D after() {
        return new Point3D(x-1,y,z);
    }

    public Point3D right() {
        return new Point3D(x,y+1,z);
    }
    public Point3D left() {
        return new Point3D(x,y-1,z);
    }

    public Stream<Point3D> neighbours() {
        return Stream.of(above(),below(),before(),after(),left(),right());
    }

    public Point3D reverse() {
        return new Point3D(-x,-y,-z);
    }

    public Point3D translate(Point3D delta) {
        return new Point3D(x+delta.x, y+delta.y,z+delta.z);
    }

    public Point3D rotate(int rotation) {
        switch (rotation) {
            case  0: return new Point3D( x,  y,  z);
            case  1: return new Point3D( x, -y, -z);
            case  2: return new Point3D( x,  z, -y);
            case  3: return new Point3D( x, -z,  y);
            case  4: return new Point3D(-x,  z,  y);
            case  5: return new Point3D(-x, -z, -y);
            case  6: return new Point3D(-x,  y, -z);
            case  7: return new Point3D(-x, -y,  z);

            case  8: return new Point3D( y,  z,  x);
            case  9: return new Point3D( y, -z, -x);
            case 10: return new Point3D( y,  x, -z);
            case 11: return new Point3D( y, -x,  z);
            case 12: return new Point3D(-y,  x,  z);
            case 13: return new Point3D(-y, -x, -z);
            case 14: return new Point3D(-y,  z, -x);
            case 15: return new Point3D(-y, -z,  x);

            case 16: return new Point3D( z,  x,  y);
            case 17: return new Point3D( z, -x, -y);
            case 18: return new Point3D( z,  y, -x);
            case 19: return new Point3D( z, -y,  x);
            case 20: return new Point3D(-z,  y,  x);
            case 21: return new Point3D(-z, -y, -x);
            case 22: return new Point3D(-z,  x, -y);
            case 23: return new Point3D(-z, -x,  y);
        }
        throw new RuntimeException("Rotation " + rotation + " not supported.");
    }

    public Point3D findTranslationTo(Point3D target) {
        return new Point3D(
                target.x-x,
                target.y-y,
                target.z-z
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return x == point3D.x && y == point3D.y && z == point3D.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "(Point3D{)" + x +", " + y +", " + z +')';
    }

    public static Point3D parse(String input) {
        String[] parts = input.split(",");
        return new Point3D(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    }

    public long manhattanDistance(Point3D other) {
        return
                Math.abs(x -other.x) +
                Math.abs(y - other.y) +
                Math.abs(z - other.z);
    }
    public long squareDistance(Point3D other) {
        return
                ((x - other.x) * (x - other.x)) +
                ((y - other.y) * (y - other.y)) +
                ((z - other.z) * (z - other.z));
    }

}
