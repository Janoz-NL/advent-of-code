package com.janoz.aoc.y2021.day15;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.geo.BorderedGrid;
import com.janoz.aoc.geo.Grid;
import com.janoz.aoc.geo.GrowingGrid;
import com.janoz.aoc.geo.Point;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Day15 {

    static Grid<Integer> grid;
    static GrowingGrid<Integer> riskGrid = new GrowingGrid<>(Integer.MAX_VALUE);

    public static void main(String[] args) {
        //Day15.read("inputs/day15.txt");
        Day15.readExpanded("inputs/2021/day15.txt");
        System.out.println(Day15.dijkstra());
    }

    public static void readExpanded(String input) {
        grid = new ExpandedGrid(readGrid(input));
    }

    public static void read(String input) {
        grid = readGrid(input);
    }

    private static BorderedGrid readGrid(String input) {
        return BorderedGrid.from(new InputProcessor<>(input, line -> line.chars().map(c -> c - '0').toArray()).stream(), Integer.MAX_VALUE);
    }

    public static int dijkstra() {
        PriorityQueue<Node> nodeQueue = new PriorityQueue<>();
        nodeQueue.add(updateNode(new Point(grid.getHeight()-1,grid.getWidth()-1),0));
        Node current;
        while (!nodeQueue.isEmpty()) {
            current = nodeQueue.poll();
            if (current.cost <= riskGrid.get(current.p)) {
                int cost = grid.get(current.p) + current.cost;
                Arrays.stream(current.p.neighbours()).filter(grid::inGrid).forEach(p -> {
                    if (riskGrid.get(p) > cost) {
                        nodeQueue.add(updateNode(p, cost));
                    }
                });
            }
        }
        return riskGrid.get(new Point(0,0));
    }

    static Node updateNode(Point p, int score) {
        riskGrid.put(p, score);
        return new Node(p, score);
    }
}
