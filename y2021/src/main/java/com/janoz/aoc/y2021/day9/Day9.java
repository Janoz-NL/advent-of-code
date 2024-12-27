package com.janoz.aoc.y2021.day9;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.collections.MergingMap;
import com.janoz.aoc.geo.BorderedGrid;
import com.janoz.aoc.geo.Point;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Day9 {

    public static void main(String[] args) {
        Day9 d = fromFile("inputs/2021/day9.txt");
        System.out.println(d.scorePart1(d.findValeys()));

        d.createBasinIds();
        System.out.println(d.scorePart2());
    }

    private int nextBasinId;
    private BorderedGrid grid;

    public Set<Point> findValeys() {
        return grid.streamPoints().filter(this::isLocalMinimum).collect(Collectors.toSet());
    }

    public int scorePart1(Set<Point> valeys) {
        int answer = 0;
        for (Point p : valeys) {
            answer += grid.get(p)+1;
        }
        return answer;
    }

    private BorderedGrid basinIds;
    private MergingMap mm = new MergingMap();

    public void createBasinIds() {
        basinIds = BorderedGrid.from(grid.getWidth(), grid.getHeight(), -1);
        nextBasinId = 1;
        grid.streamPoints().filter(p -> grid.get(p) != 9).forEach((p) -> {
            int top = grid.get(p.north());
            int left = grid.get(p.west());
            if (top == 9 && left == 9) {
                basinIds.put(p, nextBasinId++);
            } else {
                if (left != 9) {
                    basinIds.put(p, basinIds.get(p.west()));
                    if (top != 9) {
                        mm.addMapping(basinIds.get(p), basinIds.get(p.north()));
                    }
                } else {
                    basinIds.put(p, basinIds.get(p.north()));
                }
            }
        });
    }

    public int scorePart2() {
        int[] histogram = new int[nextBasinId]; // way to big
        basinIds.streamValues().filter(i -> i > 0).mapToInt(mm::getActual).forEach(i -> histogram[i]++);
        Arrays.sort(histogram);
        return histogram[nextBasinId -1] * histogram[nextBasinId -2] * histogram[nextBasinId -3];
    }


    private boolean isLocalMinimum(Point p) {
        int current = grid.get(p);
        return
                grid.get(p.north()) > current &&
                grid.get(p.east()) > current &&
                grid.get(p.south()) > current &&
                grid.get(p.west()) > current;
    }


    public static Day9 fromFile(String input) {
        Day9 d = new Day9();
        d.grid = BorderedGrid.from(new InputProcessor<>(input, line -> line.chars().map(c -> c - '0').toArray()).stream(), 9);
        return d;
    }
}
