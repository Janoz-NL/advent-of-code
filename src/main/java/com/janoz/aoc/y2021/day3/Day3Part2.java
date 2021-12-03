package com.janoz.aoc.y2021.day3;

import com.janoz.aoc.InputIterable;

import java.util.List;
import java.util.stream.StreamSupport;

public class Day3Part2 {


    public List<boolean[]> readInput(String file) {
        StreamSupport.stream(new InputIterable<>(file, new BitMapper()).spliterator(),false);
        return null;
    }
}
