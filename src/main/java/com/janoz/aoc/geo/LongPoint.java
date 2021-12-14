package com.janoz.aoc.geo;

import java.util.Objects;
import java.util.function.Function;

public class LongPoint {

    public long x,y;

    public LongPoint(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public LongPoint north() {
        return translate(0,-1);
    }

    public LongPoint south() {
        return translate(0,+1);
    }

    public LongPoint east() {
        return translate(+1,0);
    }

    public LongPoint[] adjacent() {
        return new LongPoint[]{
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
    public LongPoint west() {
        return translate(-1,0);
    }

    public LongPoint translate(int dx, int dy) { return new LongPoint(x+dx,y+dy);}

    @Override
    public String toString() {
        return x + "," + y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LongPoint point = (LongPoint) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }


    public static LongPoint parse(String input) {
        String[] parts = input.split(",");
        return new LongPoint(Long.parseLong(parts[0].trim()), Long.parseLong(parts[1].trim()));
    }

    public Point toPoint() {
        return new Point((int)x,(int)y);
    }

}
