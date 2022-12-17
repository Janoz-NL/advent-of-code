package com.janoz.aoc.y2022.day17;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.StopWatch;
import com.janoz.aoc.collections.CollectionUtils;
import com.janoz.aoc.collections.InfiniteIterator;
import com.janoz.aoc.geo.GrowingGrid;
import com.janoz.aoc.geo.Point;

import java.io.IOException;
import java.util.Iterator;

public class Day17 {


    public static final int PATTERN_SEARCH_START = 250;
    public static final int PATTERN_MIN_LENGTH = 100;
    public static final int INITIAL_STEPS = 6000;

    public static final long TOTAL_STEPS = 1000000000000L;

    static Iterator<Integer> commands;
    static GrowingGrid<Boolean> field = new GrowingGrid<>(false);
    static long totalDroppedRocks = 0;

    public static void main(String[] args) throws IOException {
        StopWatch.start();
        String file = "inputs/2022/day17.txt";
        commands = CollectionUtils.wrap(InfiniteIterator.loopingCharIterator(InputProcessor.getReaderFromResource(file).readLine()), c-> c == '<' ? -1 : 1);

        //drop some rocks
        for (int i=0; i<INITIAL_STEPS; i++) {
            if (i==2022) System.out.println("Answer part 1 :" + field.getHeight());
            dropRock();
        }

        //drop some more to be sure the height increases when block is added
        int prevHight = field.getHeight();
        while (prevHight != field.getHeight()) dropRock();

        int patternHeight = findPattern();
        int rocksInPattern = getRocksNeededForPattern(patternHeight);

        long amountOfRepeatedPatterns = (TOTAL_STEPS - totalDroppedRocks) / rocksInPattern;
        long extraHeight= amountOfRepeatedPatterns * patternHeight;
        totalDroppedRocks = totalDroppedRocks + (long)rocksInPattern * amountOfRepeatedPatterns;

        //finish it up
        while (totalDroppedRocks < TOTAL_STEPS) {
            dropRock();
        }
        StopWatch.stopPrint();
        System.out.println("Answer part 2 :" + (field.getHeight() + extraHeight));
    }

    private static int getRocksNeededForPattern(int patternSize) {
        int sizeWithNextPattern = field.getHeight() + patternSize;
        int stepsInPattern = 0;
        while (sizeWithNextPattern != field.getHeight()) {
            totalDroppedRocks++;
            stepsInPattern++;
            Rock.dropNext(field);
        }
        return stepsInPattern;
    }

    static int findPattern() {
        int[] lines = new int[INITIAL_STEPS];
        for (int y=PATTERN_SEARCH_START; y<INITIAL_STEPS; y++) {
            lines[y]= asBitmap(y);
        }

        int current = PATTERN_SEARCH_START;
        int toCompare = PATTERN_SEARCH_START + PATTERN_MIN_LENGTH;
        while (!patternFound(lines,current,toCompare)) {
            toCompare++;
        }

        return toCompare - current;
    }

    private static boolean patternFound(int[] lines, int current, int toCompare) {
        int size = toCompare - current;
        for (int i=0; i<size;i++) {
            if (lines[current + i] != lines[toCompare + i]) {
                return false;
            }
//            if (!lines[current + i].equals(lines[toCompare + i + size])) {
//                return false;
//            }
        }
        return true;
    }

    private static int asBitmap(int y) {
        int i = 0;
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x<7; x++) {
            i = i << 1;
            if (field.peek(new Point(x,y))) i=i+1;
        }
        return i;
    }

    private static void dropRock() {
        Rock.dropNext(field);
        totalDroppedRocks++;
    }
}
