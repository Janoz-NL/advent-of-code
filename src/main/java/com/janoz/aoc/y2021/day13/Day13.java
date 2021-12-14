package com.janoz.aoc.y2021.day13;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.geo.GrowingGrid;
import com.janoz.aoc.geo.Point;

import java.io.BufferedReader;
import java.util.Set;
import java.util.stream.Collectors;

public class Day13 {

    public static void main(String[] args) {
        Day13 d = new Day13();
        d.part1("inputs/day13.txt");
        System.out.println(d.count());

        d = new Day13();
        d.part2("inputs/day13.txt");
        d.print();
    }

    GrowingGrid<Boolean> paper = new GrowingGrid<>(Boolean.FALSE);

    void foldY(long y) {
        fold(paper.getWidth(), y);
    }

    void foldX(long x) {
        fold(x, paper.getHeight());
    }

    void fold(long x, long y) {
        Set<Point> mirroredPoints = paper.streamPoints()
                .filter(p -> p.x>x || p.y>y)
                .filter(p-> paper.get(p))
                .map(p -> new Point(mirror(p.x,x),mirror(p.y,y)))
                .collect(Collectors.toSet());
        mirroredPoints.forEach(p -> paper.put(p,true));
        paper.setWidth((int)x);
        paper.setHeight((int)y);
    }

    static int mirror(int pos, long mirror) {
        if (pos > mirror) return (int)(mirror - (pos - mirror));
        return pos;
    }

    long count() {
        return paper.amountOfPutPoints();
    }

    void print() {
        System.out.println(paper.toString(b -> b?"##":"  "));
    }

    void part1(String input) {
        BufferedReader reader = InputProcessor.getReaderFromResource(input);
        new InputProcessor<>(reader, Point::parse).forEach(p -> paper.put(p,true));
        new InputProcessor<>(reader, Move::parse).iterator().next().doMove(this::foldY,this::foldX);
    }

    void part2(String input) {
        BufferedReader reader = InputProcessor.getReaderFromResource(input);
        new InputProcessor<>(reader, Point::parse).forEach(p -> paper.put(p,true));
        new InputProcessor<>(reader, Move::parse).forEach(m -> m.doMove(this::foldY,this::foldX));
    }

}
