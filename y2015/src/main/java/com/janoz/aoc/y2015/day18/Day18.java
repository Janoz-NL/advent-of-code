package com.janoz.aoc.y2015.day18;

import com.janoz.aoc.geo.GrowingGrid;
import com.janoz.aoc.geo.Point;
import com.janoz.aoc.input.AocInput;

import java.util.Arrays;

public class Day18 {

    public static void main(String[] args) {
        solve(AocInput.of(2015,18));
    }

    static GrowingGrid<Boolean> grid;

    static void solve(AocInput<String> input) {
        GrowingGrid<Boolean> initial = GrowingGrid.readGrid(input.iterator(), c->c=='#',false);
        grid = initial.copy();
        for (int i=0; i<100; i++) {
            next();
        }
        System.out.println("Part 1 :" + grid.amountOfPutPoints());

        grid = initial.copy();
        for (int i=0; i<100; i++) {
            turnOnCorners();
            next();
        }
        turnOnCorners();
        System.out.println("Part 2 :" + grid.amountOfPutPoints());
    }

    private static void next() {
        GrowingGrid<Boolean> next = new GrowingGrid<>(false);
        grid.streamAllPoints().forEach(p -> {
            int adjecent = living(p);
            if (grid.get(p)) {
                next.put(p,adjecent == 2 || adjecent == 3);
            } else {
                next.put(p,adjecent == 3);
            }
        });
        grid = next;
    }

    static void turnOnCorners() {
        grid.put(new Point(0,0),true);
        grid.put(new Point(99,0),true);
        grid.put(new Point(99,99),true);
        grid.put(new Point(0,99),true);
    }

    private static int living(Point p) {
        return (int)Arrays.stream(p.adjacent()).filter(grid::peek).count();
    }
}
