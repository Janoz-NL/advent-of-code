package com.janoz.aoc.y2024.day18;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.algorithms.AStar;
import com.janoz.aoc.algorithms.Dijkstra;
import com.janoz.aoc.algorithms.PathFindingAlgorithm;
import com.janoz.aoc.geo.BoundingBox;
import com.janoz.aoc.geo.Point;
import com.janoz.aoc.graphics.ConsumingAnimationWriter;
import com.janoz.aoc.graphics.Graphics;

public class Day18 {

    static List<Point> bytes;
    static Predicate<Point> inBounds;

    public static void main(String[] args) {
        bytes = InputProcessor.asStream("inputs/2024/day18.txt", Point::parse).collect(Collectors.toList());
        BoundingBox bb = new BoundingBox(bytes);
        Point target = bb.bottomRight();
        inBounds = bb.inBoundsPredicate();

        ConsumingAnimationWriter caw = new ConsumingAnimationWriter("target/ani.gif", 50);

        Consumer<BufferedImage> bic = caw.imageConsumer();

        bic.accept(Graphics.gridImage(target.x+1, target.y+1,5,1, Color.DARK_GRAY));

        Long l = PathFindingAlgorithm.renderingGridPathFinding(target.x+1, target.y+1,
                Dijkstra.for2DGrid(validMovePredicate(1024), target::equals),
                Point.ORIGIN,
                new HashSet<>(bytes.subList(0,1024)),
                grid -> bic.accept(Graphics.toBigImage(grid, c -> c, 5, 1, BufferedImage.TYPE_INT_ARGB)));

//        Long l = AStar.renderingGridDijkstra(target.x+1, target.y+1,
//                AStar.for2DGrid(validMovePredicate(2952), target),
//                Point.ORIGIN,
//                new HashSet<>(bytes.subList(0,2953)),
//                grid -> bic.accept(grid.toBigImage(c -> c, 5,1)));

        caw.addPauze(100);
        caw.close();

        System.out.println(l);

        System.out.println("Part 1:" + AStar.for2DGrid(validMovePredicate(1024), target).calculate(Point.ORIGIN));

        int min = 1024;
        int max = bytes.size() - 1;
        while (true) {
            int i = min + ((max - min) / 2);
            if (AStar.for2DGrid(validMovePredicate(i), target).calculate(Point.ORIGIN) == null) {
                max = i;
            } else {
                min = i;
            }
            if (min + 1 == max) {
                System.out.println("Part 2:" + bytes.get(max));
                break;
            }
        }
    }

    static BiFunction<Point,Point,Boolean> validMovePredicate(int time) {
        return (from,to) -> inBounds.test(to) && !bytes.subList(0,time+1).contains(to);
    }

    static void walkWithFalingBytes() {
        bytes = InputProcessor.asStream("inputs/2024/day18.txt", Point::parse).collect(Collectors.toList());
        Point target = new Point(70,70);
        inBounds = new BoundingBox(Point.ORIGIN, target).inBoundsPredicate();

        PathFindingAlgorithm<Point> pfa = Dijkstra.for2DGrid(
                (from, to, time) -> inBounds.test(to) && !bytes.subList(0,(int) (long) time).contains(to),
                target::equals);

        pfa.calculate(Point.ORIGIN);
        System.out.println(pfa.getDistance(target));
    }
}
