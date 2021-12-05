package com.janoz.aoc;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public class MatrixUtils {

    public static int[][] transpose(int[][] input) {
        int rows = input.length;
        int cols = input[0].length;
        int[][] output = new int[cols][];
        for (int c = 0; c<cols; c++) {
            output[c] = new int[rows];
            for (int r = 0; r<rows; r++) {
                output[c][r] = input[r][c];
            }
        }
        return output;
    }

    public static int[][] map(int[][] input, int[] map) {
        int rows = input.length;
        int cols = input[0].length;
        int[][] output = new int[rows][];
        for (int r=0;r<rows;r++) {
            output[r] = new int[cols];
            for (int c=0;c<cols;c++) {
                output[r][c] = map[input[r][c]];
            }
        }
        return output;
    }

    public static int[][] read(BufferedReader input, int rows) throws IOException {
        int[][] result = new int[rows][];
        for (int r=0; r<rows; r++) {
            result[r] = readLine(input.readLine());
        }
        return result;
    }

    public static void transposeInPlace(int[][] input) {
        int size = input.length;
        int temp;
        for (int r=0;r<size;r++) {
            for (int c=r;c<size;c++) {
                temp = input[r][c];
                input[r][c] = input[c][r];
                input[c][r] = temp;
            }
        }
    }

    private static int[] readLine(String line) {
        return Arrays.stream(line.trim().split("\\s+")).mapToInt(Integer::parseInt).toArray();
    }
}
