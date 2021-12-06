package com.janoz.aoc.y2021.day6;

import java.util.Arrays;

public class Day6 {


    public static final String INPUT =
                    "5,1,1,5,4,2,1,2,1,2,2,1,1,1,4,2,2,4,1,1,1,1,1,4,1,1,1,1,1,5,3,1,4,1,1,1,1,1,4,1,5,1,1,1,4,1,2," +
                    "2,3,1,5,1,1,5,1,1,5,4,1,1,1,4,3,1,1,1,3,1,5,5,1,1,1,1,5,3,2,1,2,3,1,5,1,1,4,1,1,2,1,5,1,1,1,1," +
                    "5,4,5,1,3,1,3,3,5,5,1,3,1,5,3,1,1,4,2,3,3,1,2,4,1,1,1,1,1,1,1,2,1,1,4,1,3,2,5,2,1,1,1,4,2,1,1," +
                    "1,4,2,4,1,1,1,1,4,1,3,5,5,1,2,1,3,1,1,4,1,1,1,1,2,1,1,4,2,3,1,1,1,1,1,1,1,4,5,1,1,3,1,1,2,1,1," +
                    "1,5,1,1,1,1,1,3,2,1,2,4,5,1,5,4,1,1,3,1,1,5,5,1,3,1,1,1,1,4,4,2,1,2,1,1,5,1,1,4,5,1,1,1,1,1,1," +
                    "1,1,1,1,3,1,1,1,1,1,4,2,1,1,1,2,5,1,4,1,1,1,4,1,1,5,4,4,3,1,1,4,5,1,1,3,5,3,1,2,5,3,4,1,3,5,4," +
                    "1,3,1,5,1,4,1,1,4,2,1,1,1,3,2,1,1,4";

    public static void main(String[] args) {
        Day6 day6= new Day6();
        System.out.println(day6.calculate(INPUT, 80));
        System.out.println(day6.calculate(INPUT, 256));
    }

    private final long[] histogram = new long[9];

    public long calculate(String input, int days) {
        initHistogram(input);
        for (int i=0; i<days; i++) {
            progress();
        }
        return total();
    }

    void progress() {
        long laboring = histogram[0];
        System.arraycopy(histogram,1,histogram,0,8);
        histogram[6] += laboring;
        histogram[8] = laboring;
    }

    long total() {
        return Arrays.stream(histogram).sum();
    }

    void initHistogram(String input) {
        Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).forEach(i -> histogram[i]++);
    }
}
