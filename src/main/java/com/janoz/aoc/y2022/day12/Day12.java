package com.janoz.aoc.y2022.day12;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.StopWatch;
import com.janoz.aoc.algorithms.AStar;
import com.janoz.aoc.algorithms.Dijsktra;
import com.janoz.aoc.geo.Point;

import java.util.ArrayList;
import java.util.List;

public class Day12 {

    public static void main(String[] args) {
        String file = "inputs/2022/day12.txt";
        readField(file);

        StopWatch.start();
        AStar<Point> aStar = AStar.for2DGrid(field.get(0).length(),field.size(),Day12::isReachable,end);
        System.out.println(aStar.calculate(start));
        StopWatch.stopPrint();

        StopWatch.start();
        Dijsktra<Point> dijsktra = Dijsktra.for2DGrid(field.get(0).length(),field.size(),Day12::isReverseReachable, (p) -> getChar(p) == 'a');
        System.out.println(dijsktra.calculate(end));
        StopWatch.stopPrint();
    }

    static boolean isReverseReachable(Point to, Point from) {
        return isReachable(from, to);
    }

    static boolean isReachable(Point from, Point to) {
        char start = getChar(from);
        char end = getChar(to);
        return end <= start+1;
    }

    static List<String> field;
    static Point end,start;

    static void readField(String file) {
        field = new ArrayList<>();
        InputProcessor.asStream(file).forEach(s -> {
            int startX = s.indexOf('S');
            int endX = s.indexOf('E');
            if (startX != -1) {
                start = new Point(startX,field.size());
                char[] cs = s.toCharArray();
                cs[startX] = 'a';
                s = String.valueOf(cs);
            }
            if (endX != -1) {
                end = new Point(endX,field.size());
                char[] cs = s.toCharArray();
                cs[endX] = 'z';
                s = String.valueOf(cs);
            }
            field.add(s);
        });
    }

    static char getChar(Point p) {
        return getChar(p.x,p.y);
    }

    static char getChar(int x, int y) {
        return field.get(y).charAt(x);
    }
}