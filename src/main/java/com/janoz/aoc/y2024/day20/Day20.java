package com.janoz.aoc.y2024.day20;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.StopWatch;
import com.janoz.aoc.algorithms.PFABuilder;
import com.janoz.aoc.algorithms.PathFindingAlgorithm;
import com.janoz.aoc.geo.GrowingGrid;
import com.janoz.aoc.geo.HistoGrid;
import com.janoz.aoc.geo.Point;
import com.janoz.aoc.graphics.ConsumingAnimationWriter;
import com.janoz.aoc.graphics.Graphics;

public class Day20 {

    static final int CHEAT_COUNT_P1 = 2;
    static final int CHEAT_COUNT_P2 = 20;
    static final int MIN_SCORE = 9250;
    static GrowingGrid<Character> map;
    static Point start;
    static Point end;
    static PathFindingAlgorithm<Point> pfa;
    static PathFindingAlgorithm<Point> pfaBackwards;

    static BiConsumer<Point, Point> algorithmCallback = (a,b) -> {};
    public static void main(String[] args) {
        Iterator<String> input = InputProcessor.asIterator("inputs/2024/day20.txt");
        StopWatch.start();
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

        pfa = PFABuilder.forPoints()
                .addValidMovePredicate((src1, dst1) -> map.isEmpty(dst1))
                .asDijkstra();
        pfa.calculate(start);

        System.out.println("Part 1 :" + part1());
        System.out.println("Part 2 :" + part2());
        StopWatch.stopPrint();


        pfaBackwards = PFABuilder.forPoints()
                .addValidMovePredicate((src, dst) -> map.isEmpty(dst))
                .asDijkstra();
        pfaBackwards.calculate(end);

        ConsumingAnimationWriter caw = new ConsumingAnimationWriter("target/race___.gif", 5);

        algorithmCallback = getRenderer(caw.imageConsumer());
        part2();

        caw.close();
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
                       algorithmCallback.accept(p,p2);
                       result++;
                   }
               }
           }
       }
       return result;
   }



   static BiConsumer<Point, Point> getRenderer(Consumer<BufferedImage> big) {
        return (p1,p2) -> {
            HistoGrid<Color> grid = new HistoGrid<>(map.getWidth(), map.getHeight());
            grid.put(pfa.getPath(end), Color.GRAY);
            grid.put(pfaBackwards.getPath(p1), Color.YELLOW);
            grid.put(pfa.getPath(p2), Color.RED);
            grid.put(getShortCut(p1,p2), Color.ORANGE);

            big.accept(Graphics.toBigImage(grid,c->c,5,1, BufferedImage.TYPE_INT_RGB));

        };
   }

   //****

    static Set<Point> getShortCut(Point p1, Point p2) {
        Set<Point> result = new HashSet<>();
        result.add(p1);
        result.add(p2);
        while (!p1.equals(p2)) {
            if (p1.x != p2.x) {
                int dx = p2.x - p1.x;
                if (Math.abs(dx) <= 1) {
                    p1 = p1.translate(dx,0);
                } else {
                    p1 = p1.translate(Integer.compare(dx, 0), 0);
                    p2 = p2.translate(-Integer.compare(dx, 0), 0);
                }
            }
            result.add(p1);
            result.add(p2);
            if (p1.y != p2.y) {
                int dy = p2.y - p1.y;
                if (Math.abs(dy) <= 1) {
                    p1 = p1.translate(0,dy);
                } else {
                    p1 = p1.translate(0, Integer.compare(dy, 0));
                    p2 = p2.translate(0, -Integer.compare(dy, 0));
                }
            }
            result.add(p1);
            result.add(p2);
        }
        result.add(p2);
        return result;
    }
}
