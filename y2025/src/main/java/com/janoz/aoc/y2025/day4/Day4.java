package com.janoz.aoc.y2025.day4;

import com.janoz.aoc.collections.LongTuple;
import com.janoz.aoc.geo.GrowingGrid;
import com.janoz.aoc.geo.Point;
import com.janoz.aoc.input.AocInput;

import java.util.Arrays;
import java.util.Collection;

public class Day4 {

    public static void main(String[] args) {
        LongTuple result = solve(AocInput.of(2025,4));
        printResult(result);
    }

    static LongTuple solve(AocInput<String> input) {
        GrowingGrid<Character> map = GrowingGrid.readGrid(input.iterator());
        Collection<Point> removable = getRemovable(map);
        long part1 = getRemovable(map).size();
        int total = 0;
        while (!removable.isEmpty()) {
            total += removable.size();
            removable.forEach(p -> map.put(p,'.'));
            removable = getRemovable(map);
        }
        long part2 = total;
        return new LongTuple(part1,part2);
    }

    static Collection<Point> getRemovable(GrowingGrid<Character> map) {
        return map.streamPoints(p -> p!='.').filter(p-> {
            Point[] adjacentPoints = p.adjacent();
            long adjacent = Arrays.stream(adjacentPoints).filter(a-> map.peek(a)=='.').count();
            return adjacent > 4;
        }).toList();
    }

    static void printResult(LongTuple results) {
        System.out.printf("Part 1 : %d\nPart 2 : %d",results.getLeft(), results.getRight());
    }
}
