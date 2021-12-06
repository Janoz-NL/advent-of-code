package com.janoz.aoc.y2021.day6;

import com.janoz.aoc.Matrix;

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

    public long calculate(String input, long days) {
        Matrix<Long> start = Matrix.longMatrix(new int[][]{ initHistogram(input) });
        Matrix<Long> reproductionMatrix = Matrix.longMatrix(new int[][]
                {
                        {0, 0, 0, 0, 0, 0, 1, 0, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 1, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 1, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 1, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 1, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 1, 0}
                });
        return start.mul(reproductionMatrix.pow(days)).streamContent().reduce(Long::sum).get();
    }

    int[] initHistogram(String input) {
        final int[] histogram = new int[9];
        Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).forEach(i -> histogram[i]++);
        return histogram;
    }
}
