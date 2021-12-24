package com.janoz.aoc.y2021.day24;

import com.janoz.aoc.InputProcessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Stack;

public class Day24 {

    static int[][] algo = new int[14][];
    static char[] result = new char[14];

    public static void main(String[] args) throws IOException {
        BufferedReader reader = InputProcessor.getReaderFromResource("inputs/day24.txt");
        for (int i=0; i<14; i++) {
            algo[i] = readBlock(reader);
        }
        solve(true);
        System.out.println(result);
        solve(false);
        System.out.println(result);
    }

    static void solve(boolean highest) {
        Stack<Integer> stack = new Stack<>();
        for (int i=0; i<14; i++) {
            if (algo[i][0] == 1) stack.push(i);
            else solvePair(stack.pop(),i,highest);
        }
    }

    static void solvePair(int indexUp, int indexDown, boolean highest) {
        for (int k=1; k<10; k++) {
            int i= highest?10-k:k;
            int j = i + algo[indexUp][2] + algo[indexDown][1];
            if (j<10 && j>0) {
                result[indexUp] = (char)('0' + i);
                result[indexDown] = (char)('0' + j);
                return;
            }
        }
    }

    static int[] readBlock(BufferedReader reader) throws IOException {
        int[] result = new int[3];
        skipLines(reader,4);
        result[0] = Integer.parseInt(reader.readLine().split("z")[1].trim());
        result[1] = Integer.parseInt(reader.readLine().split("x")[1].trim());
        skipLines(reader,9);
        result[2] = Integer.parseInt(reader.readLine().split("y")[1].trim());
        skipLines(reader,2);
        return result;
    }

    static void skipLines(BufferedReader reader, int lines) throws IOException {
        for (int i=0; i<lines; i++) reader.readLine();
    }
}
