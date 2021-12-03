package com.janoz.aoc.y2021.day3;

import com.janoz.aoc.InputIterable;

import java.io.IOException;

public class Day3 {

    private final int bitmapSize;

    public static void main(String[] args) throws IOException {
        System.out.println(new Day3(12)
                .calculatePowerConsumption(
                        new InputIterable<>("inputs/day3.txt", new BitMapper())));
    }

    public Day3(int bitmapSize) {
        this.bitmapSize = bitmapSize;
    }

    public int calculatePowerConsumption(InputIterable<boolean[]> input) {
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
        boolean[] gammaBits = new boolean[bitmapSize];
        for (int i = 0; i< bitmapSize; i++) {
            gammaBits[i] = histogram[i]>0;
        }
        int gamma = toDec(gammaBits);
        int epsilon = toDec(negate(gammaBits));
        return epsilon * gamma;
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
