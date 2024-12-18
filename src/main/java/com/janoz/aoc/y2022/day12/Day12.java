package com.janoz.aoc.y2022.day12;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.StopWatch;
import com.janoz.aoc.algorithms.AStar;
import com.janoz.aoc.algorithms.BFS;
import com.janoz.aoc.algorithms.Dijkstra;
import com.janoz.aoc.algorithms.PathFindingAlgorithm;
import com.janoz.aoc.geo.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

public class Day12 {

    public static void main(String[] args) {
        PathFindingAlgorithm<Point> algo;

        String file = "inputs/2022/day12.txt";
        readField(file);

        Collection<Point> allAs = collectAs();

        System.out.println("-- Part 1 --");

        StopWatch.start();
        algo = AStar.for2DGrid(field.get(0).length(),field.size(),Day12::isReachable,end);
        System.out.println("aStar        : " + algo.calculate(start));
        StopWatch.stopPrint();

        StopWatch.start();
        algo = AStar.for2DGrid(field.get(0).length(),field.size(),Day12::isReverseReachable,start);
        System.out.println("aStar rev    : " + algo.calculate(end));
        StopWatch.stopPrint();

        StopWatch.start();
        algo = BFS.forPoints(field.get(0).length(), field.size(), Day12::isReachable, (p) -> p.equals(end));
        System.out.println("BFS          : " + algo.calculate(start));
        StopWatch.stopPrint();

        StopWatch.start();
        algo = BFS.forPoints(field.get(0).length(), field.size(), Day12::isReverseReachable, (p) -> p.equals(start));
        System.out.println("BFS rev      : " + algo.calculate(end));
        StopWatch.stopPrint();

        StopWatch.start();
        algo = Dijkstra.for2DGrid(field.get(0).length(),field.size(),Day12::isReachable, (p) -> p.equals(end));
        System.out.println("Dijkstra     : " + algo.calculate(start));
        StopWatch.stopPrint();

        StopWatch.start();
        algo = Dijkstra.for2DGrid(field.get(0).length(),field.size(),Day12::isReverseReachable, (p) -> p.equals(start));
        System.out.println("Dijkstra rev : " + algo.calculate(end));
        StopWatch.stopPrint();

        System.out.println("-- Part 2 --");

        StopWatch.start();
        algo = AStar.for2DGrid(field.get(0).length(),field.size(),Day12::isReachable,end);
        System.out.println("aStar rev    : " + algo.calculate(allAs));
        StopWatch.stopPrint();

        StopWatch.start();
        algo = BFS.forPoints(field.get(0).length(), field.size(), Day12::isReachable, (p) -> p.equals(end));
        System.out.println("BFS          : " + algo.calculate(allAs));
        StopWatch.stopPrint();

        StopWatch.start();
        algo = BFS.forPoints(field.get(0).length(), field.size(), Day12::isReverseReachable, (p) -> getChar(p) == 'a');
        System.out.println("BFS rev      : " + algo.calculate(end));
        StopWatch.stopPrint();

        StopWatch.start();
        algo = Dijkstra.for2DGrid(field.get(0).length(),field.size(), Day12::isReachable, (p) -> p.equals(end));
        System.out.println("Dijkstra     : " + algo.calculate(allAs));
        StopWatch.stopPrint();

        StopWatch.start();
        algo = Dijkstra.for2DGrid(field.get(0).length(),field.size(), Day12::isReverseReachable, (p) -> getChar(p) == 'a');
        System.out.println("Dijkstra rev : " + algo.calculate(end));
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

    static Collection<Point> collectAs() {
        Collection<Point> result = new ArrayList<>();
        IntStream.range(0,field.size())
                .forEach(y -> IntStream.range(0,field.get(0).length())
                        .filter(x -> getChar(x,y) == 'a')
                        .mapToObj(x -> new Point(x,y))
                        .forEach(result::add));
        return result;
    }

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