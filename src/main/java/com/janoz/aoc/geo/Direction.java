package com.janoz.aoc.geo;

public class Direction extends Point{


    public static final Direction NORTH = new Direction(0,-1);
    public static final Direction EAST = new Direction(1,0);
    public static final Direction SOUTH = new Direction(0,1);
    public static final Direction WEST = new Direction(-1,0);

    private Direction(int dx, int dy) {
        super(dx,dy);
    }

    public  Direction rotateCW() {
        return new Direction(this.y * -1, this.x);
    }

    public  Direction rotateCCW() {
        return new Direction(this.y, this.x * -1);
    }


}
