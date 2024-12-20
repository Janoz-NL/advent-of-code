package com.janoz.aoc.y2024.day20;

import java.util.Iterator;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.algorithms.Dijkstra;
import com.janoz.aoc.algorithms.PathFindingAlgorithm;
import com.janoz.aoc.geo.GrowingGrid;
import com.janoz.aoc.geo.Point;

public class Day20 {

    static final int CHEAT_COUNT_P1 = 2;
    static final int CHEAT_COUNT_P2 = 20;
    static final int MIN_SCORE = 100;
    static GrowingGrid<Character> map;
    static Point start;
    static Point end;
    static PathFindingAlgorithm<Point> pfa;
    public static void main(String[] args) {
        Iterator<String> input = InputProcessor.asIterator("inputs/2024/day20.txt");
        map = GrowingGrid.readGrid(input, (p, c) -> {
            switch (c) {
                case 'E':
                    end = p;
                    break;
                case 'S':
                    start = p;
                    break;
            }
        });
        map.put(start,'.');
        map.put(end,'.');

        pfa = Dijkstra.for2DGrid((src, dst) -> map.isEmpty(dst));
        pfa.calculate(start);

        System.out.println("Part 1 :" + part1());
        System.out.println("Part 2 :" + part2());
   }

   static long part1() {
       return map.streamAllPoints().filter(map::isEmpty).mapToLong(p ->Day20.cheatGain(p,CHEAT_COUNT_P1)).sum();
   }

   static long part2() {
       return map.streamAllPoints().filter(map::isEmpty).mapToLong(p -> Day20.cheatGain(p,CHEAT_COUNT_P2)).sum();
   }

    static long cheatGain(Point p, int distance) {
       long result = 0;
       for (int dx = -distance; dx<= distance; dx++) {
           int stepsLeft = distance - Math.abs(dx);
           for (int dy = -stepsLeft; dy<= stepsLeft; dy++) {
               Point p2 = p.translate(dx,dy);
               if (map.inGrid(p2) && map.isEmpty(p2)) {
                   long profit = pfa.getDistance(p) - pfa.getDistance(p2) - p.manhattanDistance(p2);
                   if (profit >= MIN_SCORE) {
                       result++;
                   }
               }
           }
       }
       return result;
   }
}
