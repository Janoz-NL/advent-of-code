package com.janoz.aoc.y2024.day2;

import com.janoz.aoc.input.AocInput;
import com.janoz.aoc.math.IntMatrixUtils;

public class Day2 {

    public static void main(String[] args) {
        System.out.println(solve(AocInput.of(2024,2)));
    }


    static int solve(AocInput<String> input) {
        return input.stream().map(IntMatrixUtils::readLine).mapToInt(Day2::isDamperSave).sum();
        //return InputProcessor.asStream(file, IntMatrixUtils::readLine).mapToInt(Day2::isDamperSave).sum();
    }

    static int isSave(int[] input) {
        if (input[0] < input[1]) {
            return isSaveIncreasing(input);
        } else if (input[0] > input[1]) {
            return isSaveDecreasing(input);
        } else return 0;
    }

    static int isSaveIncreasing(int[] input) {
        for (int i=1; i<input.length;i++) {
            if (input[i-1] >= input[i] || input[i-1] < input[i]-3) {
                return 0;
            }
        }
        return 1;
    }

    static int isSaveDecreasing(int[] input) {
        for (int i=1; i<input.length;i++) {
            if (input[i-1] <= input[i] || input[i-1] > input[i]+3) {
                return 0;
            }
        }
        return 1;
    }


    static int isDamperSave(int[] input) {
        int[] damped = new int[input.length-1];
        for (int i=0; i<input.length;i++) {
            for (int j=0; j< damped.length; j++) {
                int t = i <= j? j+1:j;
                damped[j] = input[t];
            }
            if (isSave(damped) == 1) {
                return 1;
            }
        }
        return 0;
    }



}


