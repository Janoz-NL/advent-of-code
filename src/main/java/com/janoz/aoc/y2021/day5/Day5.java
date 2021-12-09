package com.janoz.aoc.y2021.day5;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.geo.GrowingGrid;
import com.janoz.aoc.geo.Line;

import java.util.function.Function;
import java.util.stream.Stream;

public class Day5 {

    public static void main(String[] args) {
        System.out.println(new Day5().calculateScore1("inputs/day5.txt"));
        System.out.println(new Day5().calculateScore2("inputs/day5.txt"));
    }

    public long calculateScore1(String input) {
        return calculate(streamLines(input).filter(Line::isStraight));
    }

    public long calculateScore2(String input) {
        return calculate(streamLines(input));
    }

    private long calculate(Stream<Line> lines) {
        GrowingGrid<Integer> field = new GrowingGrid<>(0);
        lines.flatMap(Line::draw).forEach(p -> p.putOn(field, (Function<Integer, Integer>) i -> (i+1) ));
        return field.streamValues().filter(i -> i > 1).count();

    }

    public Stream<Line> streamLines(String file) {
        return new InputProcessor<>(file,Line::parse).stream();
    }
}
