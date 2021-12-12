package com.janoz.aoc.y2021.day11;

import com.janoz.aoc.geo.BorderedGrid;
import com.janoz.aoc.geo.Point;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Day11 {

    BorderedGrid grid;

    public static void main(String[] args) {
        Day11 d = new Day11();
        d.load("inputs/day11.txt");
//        System.out.println(d.part1());
        System.out.println(d.part2());
    }

    long part1() {
        for (int i=0;i<100;i++) step();
        return flashes;
    }

    long part2() {
        BorderedGrid superflash = BorderedGrid.filled(10,10,0,-1);
        long step = 0;
        while (!grid.equals(superflash)){
            step++;
            step();
        }
        return step;
    }


    void step() {
        flash();
        incfield();
    }


    void incfield() {
        grid.streamPoints().forEach(p -> grid.put(p, i->i+1));
    }

    long flashes = 0;
    void flash() {
        Set<Point> flashers = grid.streamPoints().filter(p -> grid.get(p) >= 9).collect(Collectors.toSet());
        flashes += flashers.size();
        while (!flashers.isEmpty()) {
            flashers.forEach(p -> {
                Arrays.stream(p.adjacent()).forEach(np -> grid.put(np,i -> i==-1?-1:i+1));
                grid.put(p,-1);
            });
            flashers = grid.streamPoints().filter(p -> grid.get(p) >= 9).collect(Collectors.toSet());
            flashes += flashers.size();
        }
    }

    void load(String input) {
        grid = BorderedGrid.fromSingleDigitFile(input,-1);
    }



}
