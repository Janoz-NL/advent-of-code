package com.janoz.aoc.y2015.day3;

import java.util.HashSet;
import java.util.Set;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.geo.Point;

public class Day3 {

    public static void main(String[] args) {
        String directions = InputProcessor.asIterator("inputs/2015/day3.txt").next();
        Set<Point> visited = new HashSet<>();
        Point curPos = Point.ORIGIN;
        visited.add(curPos);
        for (char c:directions.toCharArray()) {
            curPos = curPos.move(c);
            visited.add(curPos);
        }
        System.out.println("Part 1 : " + visited.size());

        visited.clear();
        Point santa = Point.ORIGIN;
        Point robot = Point.ORIGIN;
        visited.add(Point.ORIGIN);
        for (int i=0; i<directions.length(); i+=2) {
            santa = santa.move(directions.charAt(i));
            robot = robot.move(directions.charAt(i+1));
            visited.add(santa);
            visited.add(robot);
        }
        System.out.println("Part 2 : " + visited.size());
    }
}
