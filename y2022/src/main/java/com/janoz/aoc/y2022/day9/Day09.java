package com.janoz.aoc.y2022.day9;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.StopWatch;
import com.janoz.aoc.geo.BoundingBox;
import com.janoz.aoc.geo.Point;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Day09 {

    public static void main(String[] args) {
        //String file = "inputs/2022/day09.txt";
        String file = "inputs/2022/bigsets/aoc_2022_day09_large-2.in";
        Day09 solver = new Day09(10);
        StopWatch.start();
        InputProcessor.asStream(file, Move::parse).forEach(solver::doMove);
        StopWatch.stopPrint();
        System.out.println("Part 1: " + solver.visitedPart1.size());
        System.out.println("Part 2: " + solver.visitedPart2.size());
    }

    Day09(int ropeLength) {
        this.ropeLength = ropeLength;
        rope = new Point[ropeLength];
        Arrays.fill(rope, new Point(0,0));
        visitedPart1 = new HashSet<>();
        visitedPart1.add(new Point(0,0));
        visitedPart2 = new HashSet<>();
        visitedPart2.add(new Point(0,0));
    }

    Set<Point> visitedPart2;
    Set<Point> visitedPart1;
    Point[] rope;
    int ropeLength;

    void doMove(Move m) {
        for (int step = 0; step<m.steps; step++) {
            rope[0] = m.dir.move(rope[0]);
            for (int i=1; i < ropeLength; i++) {
                if (!rope[i-1].isTouching(rope[i])) {
                    rope[i] = moveKnot(rope[i-1], rope[i]);
                }
            }
            visitedPart1.add(rope[1]);
            visitedPart2.add(rope[ropeLength-1]);
        }
    }

    Point moveKnot(Point pulling, Point pulled) {
        int dx = (int)Math.signum(pulling.x - pulled.x);
        int dy = (int)Math.signum(pulling.y - pulled.y);
        return pulled.translate(dx,dy);
    }


    //---------------------//
    BoundingBox bb = new BoundingBox(new Point(0,0));

    void printGrid() {
        bb.addPoint(rope[0]);
        bb.printGrid(p -> {
            if (p.equals(rope[0])) return 'H';
            for (int i=1; i<=9; i++) {
                if (p.equals(rope[i])) return (char)('0' + i);
            }
            return visitedPart2.contains(p)?'#':'.';
        });
        System.out.println();
        System.out.println();
        System.out.println();
    }
}
