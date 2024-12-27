package com.janoz.aoc.y2024.day15;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.geo.Direction;
import com.janoz.aoc.geo.GrowingGrid;
import com.janoz.aoc.geo.Point;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Day15 {

    public static void main(String[] args) {
        Iterator<String> input = InputProcessor.asIterator("inputs/2024/day15.txt");
        loadMap(input);
        constructMap2();
        loadMoves(input);

        walk();
        System.out.println("Part1 : " + calcScore());

        part1 = false;
        map = map2;
        robot = new Point(start.x *2, start.y);
        walk();
        System.out.println("Part2 : " + calcScore());
    }

    static GrowingGrid<Character> map;
    static String moves;
    static Point robot;
    static GrowingGrid<Character> map2;
    static Point start;
    static boolean part1 = true;

    static void loadMap(Iterator<String> input) {
        map = GrowingGrid.readGrid(input, (p,c) -> {
            if (c == '@') {
                robot = p;
            }
        });
        map.put(robot,'.');
        start = robot;
    }

    static void constructMap2() {
        map2 = new GrowingGrid<>('.');
        map.streamPoints(c -> c == '#').forEach(p -> {
            Point p1 = new Point(p.x*2, p.y);
            Point p2 = p1.east();
            map2.put(p1,'#');
            map2.put(p2,'#');
        });
        map.streamPoints(c -> c == 'O').forEach(p -> {
            Point p1 = new Point(p.x*2, p.y);
            Point p2 = p1.east();
            map2.put(p1,'[');
            map2.put(p2,']');
        });
    }

    static void loadMoves(Iterator<String> input) {
        StringBuilder sb = new StringBuilder();
        while (input.hasNext()) {
            sb.append(input.next());
        }
        moves = sb.toString();
    }

    static void walk() {
        for (char c : moves.toCharArray()) {
            Direction d = getDirection(c);
            if (d.y==0 || part1) {
                if (move(robot, d)) {
                    robot = robot.translate(d);
                }
            } else {
                moveBigVertical(d);
            }
        }
    }

    static void moveBigVertical(Direction d) {
        Set<Point> boxes = new HashSet<>();
        if (canMove(robot,d, boxes)) {
            doMove(boxes, d);
            robot = robot.translate(d);
        }
    }

    static boolean canMove(Point p, Direction d, Set<Point> boxes) {
        Point newPos = p.translate(d);
        char c = map.get(newPos);
        if (c == '[') {
            boxes.add(newPos);
            return canMove(newPos,d,boxes) && canMove(newPos.east(),d,boxes);
        } else if (c == ']') {
            boxes.add(newPos.west());
            return canMove(newPos, d,boxes) && canMove(newPos.west(), d, boxes);
        } else return c == '.';
    }

    static void doMove(Set<Point> boxes, Direction d) {
        //remove them
        boxes.forEach(p -> {
            map.put(p,'.');
            map.put(p.east(),'.');
        });
        //add them
        boxes.forEach(p -> {
            map.put(p.translate(d),'[');
            map.put(p.translate(d).east(),']');
        });
    }

    static long calcScore() {
        return map.streamPoints(c -> c=='O' || c=='[').mapToLong(p -> 100L*p.y + p.x).sum();
    }

    static boolean move(Point p , Direction direction) {
        Point newPos=p.translate(direction);
        char c = map.get(newPos);
        if (c == '.') {
            map.put(newPos,map.get(p));
            return true;
        }
        if (c == '#') return false;
        if (move(newPos, direction)) {
            map.put(newPos,map.get(p));
            return true;
        } else {
            return false;
        }
    }

    static Direction getDirection(char c) {
        switch (c) {
            case '^':
                return Direction.NORTH;
            case '>':
                return Direction.EAST;
            case 'v':
                return Direction.SOUTH;
            case '<':
                return Direction.WEST;
            default: throw new RuntimeException("Unknown direction");
        }
    }
}
