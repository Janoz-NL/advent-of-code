package com.janoz.aoc.y2021.day21;

import java.util.Iterator;

public class Dice implements Iterator<Integer> {



    int current = 0;

    int timesRolled = 0;

    @Override
    public boolean hasNext() {
        return true;
    }

     @Override
     public Integer next() {
        timesRolled++;
        if (current > 99) {
            current = 0;
        }
        return ++current;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
