package com.janoz.aoc.y2022.day9;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.geo.Point;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Day09 {

    public static void main(String[] args) {
        String file = "inputs/2022/day09.txt";
        Day09 solver = new Day09(10);
        InputProcessor.asStream(file, Move::parse).forEach(solver::doMove);
        System.out.println(solver.visited.size());
    }

    Day09(int ropeLength) {
        this.ropeLength = ropeLength;
        rope = new Point[ropeLength];
        Arrays.fill(rope, new Point(0,0));
        visited = new HashSet<>();
        visited.add(new Point(0,0));
    }

    Set<Point> visited;
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
            visited.add(rope[ropeLength-1]);
        }
    }

    static Point moveKnot(Point pulling, Point pulled) {
        int dx = (int)Math.signum(pulling.x - pulled.x);
        int dy = (int)Math.signum(pulling.y - pulled.y);
        return pulled.translate(dx,dy);
    }
}
