package com.janoz.aoc.y2021.day17;

import com.janoz.aoc.geo.Point;

import java.util.stream.IntStream;

public class Day17 {

    public static Square target;

    public static void main(String[] args) {
        target = new Square(new Point(230,-57), new Point(283,-107));
        int maxY = maxYVelocity();
        System.out.println(getTop(maxY));
        System.out.println(
                IntStream
                        .rangeClosed(1,target.bottomRight.x)
                        .filter(Day17::possibleX)
                        .mapToLong(x ->
                                IntStream
                                        .rangeClosed(target.bottomRight.y,maxY)
                                        .filter(y->simulate(x,y))
                                        .count())
                        .sum());
    }

    static int maxYVelocity() {
        return Integer.max(
                //top
                target.topLeft.y,
                //bottom
                (-1 * target.bottomRight.y) -1
        );
    }

    static boolean simulate(int velocityX, int velocityY) {
        Point probe = new Point(0,0);
        while (!target.overShot(probe)) {
            if (target.containsPoint(probe)) return true;
            probe = probe.translate(velocityX,velocityY);
            velocityX -= Math.signum(velocityX);
            velocityY -= 1;
        }
        return false;
    }

    static boolean possibleX(int vx) {
        int dx =0;
        while (dx <= target.bottomRight.x) {
            if (dx >= target.topLeft.x) return true;
            if (vx == 0) return false;
            dx += vx;
            vx -= Math.signum(vx);
        }
        return false;
    }

    static int getTop(int velocityY) {
        int result =0;
        while (velocityY>0) {
            result += velocityY;
            velocityY--;
        }
        return result;
    }
}
