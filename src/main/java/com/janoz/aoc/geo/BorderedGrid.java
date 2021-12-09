package com.janoz.aoc.geo;

import java.util.function.Function;
import java.util.stream.Stream;

public class BorderedGrid implements Grid<Integer>{

    private int border;
    private int width;
    private int height;
    private int[][] grid;

    public BorderedGrid(int width, int height, int border) {
        this.width = width;
        this.height = height;
        this.border = border;
        grid = create2D(width, height);
    }


    @Override
    public void put(Point p, Integer v) {
        if (contains(p)) {
            grid[p.y][p.x] = v;
        }
    }

    @Override
    public Integer put(Point p, Function<Integer, Integer> f) {
        if (contains(p)) {
            return grid[p.y][p.x] = f.apply(grid[p.y][p.x]);
        }
        return null;
    }

    @Override
    public Integer get(Point p) {
        if (!contains(p)) return border;
        return grid[p.y][p.x];
    }
    @Override
    public Stream<Integer> streamValues() {
        return null;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }


    private boolean contains(Point p) {
        return !(p.x < 0 || p.x >= width || p.y < 0 || p.y >= height);
    }


    private static int[][] create2D(int width, int height) {
        int[][] result = new int[height][];
        for (int y=0;y<height; y++) {
            result[y]=new int[width];
        }
        return result;
    }

}
