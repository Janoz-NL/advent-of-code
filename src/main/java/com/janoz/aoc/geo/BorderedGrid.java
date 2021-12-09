package com.janoz.aoc.geo;

import java.util.function.Function;
import java.util.stream.Stream;

public class BorderedGrid implements Grid<Integer>{

    private int border;
    private int width;
    private int height;
    private int[][] grid;

    private BorderedGrid(int[][] grid, int border) {
        this.width = grid[0].length;
        this.height = grid.length;
        this.border = border;
        this.grid = grid;
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


    public static BorderedGrid from(int[][] grid, int border) {
        return new BorderedGrid(grid,border);
    }

    public static BorderedGrid from(int width, int height, int border) {
        return new BorderedGrid(create2D(width,height),border);
    }

    public static BorderedGrid from(Stream<int[]> rowStreamer, int border) {
        return new BorderedGrid(rowStreamer.toArray(int[][]::new),border);
    }


    public void print() {
        for (int y=0;y<height;y++) {
            for (int x=0; x<width; x++) {
                System.out.printf("%5d",get(new Point(x,y)));
            }
            System.out.println();
        }
    }

}
