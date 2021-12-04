package com.janoz.aoc.y2021.day3;

import com.janoz.aoc.InputProcessor;

import java.util.List;
import java.util.stream.Collectors;

public class Day3 {

    private final int bitmapSize;

    public static void main(String[] args) {
        System.out.println(new Day3(12).part1("inputs/day3.txt"));
        System.out.println(new Day3(12).part2("inputs/day3.txt"));
    }

    public int part1(String input) {
        return calculatePowerConsumption(
                        new InputProcessor<>(input, new BitMapper()));
    }

    public int part2(String input) {
        List<boolean[]> inputData = new InputProcessor<>(input, new BitMapper()).asList();
        return calculateOxygen(inputData) * calculateCo2(inputData);
    }

    public Day3(int bitmapSize) {
        this.bitmapSize = bitmapSize;
    }


    public int calculateOxygen(List<boolean[]> input) {
        int bitpos = 0;
        while(input.size()>1) {
            input = filter(input,bitpos,modusBits(input)[bitpos]);
            bitpos++;
        }
        return toDec(input.get(0));
    }


    public int calculateCo2(List<boolean[]> input) {
        int bitpos = 0;
        while(input.size()>1) {
            input = filter(input,bitpos,!modusBits(input)[bitpos]);
            bitpos++;
        }
        return toDec(input.get(0));
    }


    List<boolean[]> filter(List<boolean[]> input, int bitpos, boolean b) {
        return input.stream().filter(e -> e[bitpos] == b).collect(Collectors.toList());
    }

    public int calculatePowerConsumption(Iterable<boolean[]> input) {
        boolean[] gammaBits = modusBits(input);
        int gamma = toDec(gammaBits);
        int epsilon = toDec(negate(gammaBits));
        return epsilon * gamma;
    }


    boolean[] modusBits(Iterable<boolean[]> input) {
        int[] histogram = new int[bitmapSize];
        for (boolean[] bits: input) {
            for (int i = 0; i< bitmapSize; i++) {
                if (bits[i]) {
                    histogram[i]++;
                } else {
                    histogram[i]--;
                }
            }
        }
        boolean[] modusBits = new boolean[bitmapSize];
        for (int i = 0; i< bitmapSize; i++) {
            modusBits[i] = histogram[i]>=0;
        }
        return modusBits;
    }



    private boolean[] negate(boolean[] input) {
        boolean[] result = new boolean[input.length];
        for (int i=0;i<input.length;i++) {
            result[i] = !input[i];
        }
        return result;
    }

    private int toDec(boolean[] input) {
        int result = 0;
        for (boolean b : input) {
            result *= 2;
            if (b) {
                result++;
            }
        }
        return result;
    }
}
