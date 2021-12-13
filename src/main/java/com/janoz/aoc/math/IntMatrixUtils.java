package com.janoz.aoc.math;

import com.janoz.aoc.geo.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

public class IntMatrixUtils {

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

    public static boolean isSquare(int[][] input) {
        return input.length == input[0].length;
    }

    public static int[][] mul(int[][] left, int[][] right) {
        int rows = left.length;
        int itt = right.length;
        int cols = right[0].length;
        int[][] result = new int[rows][];
        for (int r=0; r < rows; r++) {
            result[r] = new int[cols];
            for (int c=0; c < cols; c++) {
                result[r][c] = 0;
                for (int i=0; i<itt; i++) {
                    result[r][c] += left[r][i] * right[i][c];
                }
            }
        }
        return result;
    }

    public static int[][] pow(int[][] matrix, int pow) {
        if (pow == 1) return matrix;
        int half = pow/2;
        return mul(pow(matrix, half), pow(matrix, pow-half));
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

    public static IntStream streamContent(int[][] input) {
        return Arrays.stream(input).flatMapToInt(Arrays::stream);
    }

    private static int[] readLine(String line) {
        return Arrays.stream(line.trim().split("\\s+")).mapToInt(Integer::parseInt).toArray();
    }

    public static void print(int[][] matrix) {
        for (int[] row : matrix) {
            for (int i : row) {
                System.out.printf(" %5d", i);
            }
            System.out.println();
        }
    }

    public static int[][] fromPoint(Point p) {
        return new int[][] {{p.x},{p.y},{1}};
    }

    public static Point toPoint(int[][] matrix) {
        return new Point(matrix[0][0],matrix[1][0]);
    }

    public static int[][] translation(int dx, int dy) {
        return new int[][]
                {
                        { 1, 0, dx},
                        { 0, 1, dy},
                        { 0, 0,  1}
                };
    }

    public static int[][] mirrorX() {
        return new int[][]
                {
                        {-1, 0, 0},
                        { 0, 1, 0},
                        { 0, 0, 1}
                };

    }

    public static int[][] mirrorY() {
        return new int[][]
                {
                        { 1, 0, 0},
                        { 0,-1, 0},
                        { 0, 0, 1}
                };

    }

}
