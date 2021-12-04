package com.janoz.aoc.y2021.day4;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public class Board {

    private static final int SIZE = 5;

    int[][] numbers = new int[SIZE][];
    int[][] positions = new int[SIZE][];
    int[][] flippedPositions;

    int winsInTurn;

    public Board(BufferedReader input, int[] positionMap) throws IOException {
        for (int y=0;y<SIZE;y++) {
            numbers[y] = fromLine(input.readLine().trim());
            positions[y] = new int[SIZE];
            for (int x=0;x<SIZE;x++) {
                positions[y][x] = positionMap[numbers[y][x]];
            }
        }
        flippedPositions = flip(positions);
        winsInTurn = winsInTurn();
    }

    private int[] fromLine(String line) {
        return Arrays.stream(line.split("\\s+")).mapToInt(Integer::parseInt).toArray();
    }


    private int[][] flip(int [][] input) {
        int[][] output = new int[SIZE][];
        for (int x=0;x<SIZE;x++) {
            output[x] = new int[SIZE];
            for (int y=0;y<SIZE;y++) {
                output[x][y] = input[y][x];
            }
        }
        return output;
    }

    private int winsInTurn() {
        return Math.min(winsInTurn(positions), winsInTurn(flippedPositions));
    }

    public int getScore() {
        int score = 0;
        for (int y=0;y<SIZE;y++) {
            for (int x=0;x<SIZE;x++) {
                if (positions[y][x] > winsInTurn) {
                    score += numbers[y][x];
                }
            }
        }
        return score;
    }

    private int winsInTurn(int[][] values) {
        return Arrays.stream(values).mapToInt(this::max).min().getAsInt();
    }

    private int max(int[] array) {
        return Arrays.stream(array).max().getAsInt();
    }
}
