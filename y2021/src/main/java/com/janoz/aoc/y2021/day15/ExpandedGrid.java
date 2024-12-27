package com.janoz.aoc.y2021.day15;

import com.janoz.aoc.geo.BorderedGrid;
import com.janoz.aoc.geo.Grid;
import com.janoz.aoc.geo.Point;

import java.util.function.Function;

public class ExpandedGrid implements Grid<Integer> {

    BorderedGrid backing;
    int size;

    public ExpandedGrid(BorderedGrid backing) {
        this(backing, 5);
    }

    public ExpandedGrid(BorderedGrid backing, int size) {
        this.backing = backing;
        this.size = size;
    }

    @Override
    public Integer get(Point p) {
        int delta = p.x / backing.getWidth() + p.y / backing.getHeight();
        return specialModulus(backing.get(p.x % backing.getWidth(), p.y % backing.getHeight()) + delta);
    }

    private int specialModulus(int i) {
        while (i>9) {
            i = i - 9;
        }
        return i;
    }

    @Override
    public int getWidth() {
        return size * backing.getWidth();
    }

    @Override
    public int getHeight() {
        return size * backing.getWidth();
    }


    @Override
    public void put(Point p, Integer v) {
        throw new UnsupportedOperationException("Nope");
    }

    @Override
    public Integer put(Point p, Function<Integer, Integer> f) {
        throw new UnsupportedOperationException("Nope");
    }
}