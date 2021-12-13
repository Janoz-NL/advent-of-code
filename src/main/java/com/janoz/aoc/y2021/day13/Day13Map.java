package com.janoz.aoc.y2021.day13;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.geo.GrowingGrid;
import com.janoz.aoc.geo.Point;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day13Map {

    public static void main(String[] args) {
        part2("inputs/day13.txt");
    }

    static Map<Integer, Integer> yMap= new HashMap<>();
    static Map<Integer, Integer> xMap= new HashMap<>();

    static void fold(int f, Map<Integer, Integer> map) {
        map.keySet().stream().filter(k -> map.get(k) > f).forEach(k -> map.put(k, f-(map.get(k)-f)));
    }

    static void part2(String input) {
        BufferedReader reader = InputProcessor.getReaderFromResource(input);
        List<Point> points = new InputProcessor<>(reader, Point::parse).stream()
                .peek(p-> { yMap.put(p.y,p.y); xMap.put(p.x,p.x); })
                .collect(Collectors.toList());
        new InputProcessor<>(reader, Move::parse)
                .forEach(m -> m.doMove(y -> fold(y,yMap), x -> fold(x,xMap)));
        GrowingGrid<Boolean> paper = new GrowingGrid<>(false);
        points.stream().map(p -> new Point(xMap.get(p.x), yMap.get(p.y))).forEach(p -> p.putOn(paper,true));
        System.out.println(paper.toString(b -> b?"##":"  "));
    }
}
