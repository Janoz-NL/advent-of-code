package com.janoz.aoc.y2025.day7;

import com.janoz.aoc.StopWatch;
import com.janoz.aoc.collections.LongTuple;
import com.janoz.aoc.geo.GrowingGrid;
import com.janoz.aoc.geo.Point;
import com.janoz.aoc.input.AocInput;

import java.util.HashMap;
import java.util.Map;

public class Day7 {

    public static void main(String[] args) {
        StopWatch.start();
        LongTuple result = solve(AocInput.of(2025,7));
        StopWatch.stopPrint();
        printResult(result);
    }

    static Point start;

    public static LongTuple solve(AocInput<String> stringAocInput) {
        GrowingGrid<Character> map = GrowingGrid.readGrid(stringAocInput.iterator(), (p,c) -> {
            if (c == 'S') {
                start = p;
            }
        });
        Map<Integer, Long> beams = new HashMap<>();
        beams.put(start.x,1L);
        int splits = 0;
        for (int y=start.y+1; y< map.getHeight(); y++) {
            Map<Integer, Long> newBeams = new HashMap<>();
            for (int x : beams.keySet()) {
                if (map.get(new Point(x, y)) == '^') {
                    newBeams.merge(x+1, beams.get(x), Long::sum);
                    newBeams.merge(x-1, beams.get(x), Long::sum);
                    splits++;
                } else {
                    newBeams.merge(x, beams.get(x), Long::sum);
                }
            }
            beams = newBeams;
        }

        return new LongTuple(
                splits,
                beams.values().stream().mapToLong(Long::longValue).sum());
    }

    static void printResult(LongTuple results) {
        System.out.printf("Part 1 : %d\nPart 2 : %d",results.getLeft(), results.getRight());
    }
}
