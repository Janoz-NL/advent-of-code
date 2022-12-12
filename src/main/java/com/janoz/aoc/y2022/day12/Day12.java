package com.janoz.aoc.y2022.day12;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.geo.DijsktraGrid;
import com.janoz.aoc.geo.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Day12 {


    public static void main(String[] args) {
        String file = "inputs/2022/day12.txt";
        readField(file);
        //part 1
        DijsktraGrid dijkstraGrid = new DijsktraGrid(field.get(0).length(),field.size(),Day12::isReachable);
        dijkstraGrid.calculateFromEnd(end);
        System.out.println(dijkstraGrid.getDistanceToEnd(start));

        //part 2
        long result = Long.MAX_VALUE;
        for (int y=0; y<field.size(); y++) {
            for (int x=0; x<field.get(0).length(); x++) {
                if (getChar(x,y) == 'a') {
                    result = Math.min(result,dijkstraGrid.getDistanceToEnd(new Point(x,y)));
                }
            }
        }
        System.out.println(result);
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