package com.janoz.aoc.y2021.day17;

import com.janoz.aoc.geo.Point;

public class Square {


    Point topLeft;
    Point bottomRight;

    public Square(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    boolean containsPoint(Point p) {
        return
                p.x >= topLeft.x && p.x <= bottomRight.x &&
                p.y <= topLeft.y && p.y >= bottomRight.y;
    }

    boolean overShot(Point p) {
        return p.x > bottomRight.x || p.y < bottomRight.y;

    }
}
