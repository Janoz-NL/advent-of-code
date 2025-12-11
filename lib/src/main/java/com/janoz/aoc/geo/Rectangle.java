package com.janoz.aoc.geo;

public class Rectangle extends BoundingBox {

    public Rectangle(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight);
    }

    public Rectangle(BoundingBox boundingBox) {
        this.top = boundingBox.top;
        this.bottom = boundingBox.bottom;
        this.left = boundingBox.left;
        this.right = boundingBox.right;
    }

    /**
     * tile based area. ranges are inclusive
     */
    public long area() {
        return (right-left+1L) * (bottom-top+1L);
    }

    public boolean contains(StraightLine l) {
        return l.hasPointsInside(this);
    }

    public Point centerish() {
        return new Point((left+right)/2, (top+bottom)/2);
    }
}
