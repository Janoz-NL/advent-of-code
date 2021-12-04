package com.janoz.aoc.y2021.day4;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public class Board {

    final int size;

    int[][] numbers;
    int[][] positions;
    int[][] flippedPositions;

    int winsInTurn;

    public Board(BufferedReader input, int[] positionMap, int size) throws IOException {
        this.size = size;
        numbers = new int[size][];
        positions = new int[size][];
        for (int y = 0; y< size; y++) {
            numbers[y] = fromLine(input.readLine().trim());
            positions[y] = new int[size];
            for (int x = 0; x< size; x++) {
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
        int[][] output = new int[size][];
        for (int x = 0; x< size; x++) {
            output[x] = new int[size];
            for (int y = 0; y< size; y++) {
                output[x][y] = input[y][x];
            }
        }
        return output;
    }

    private int winsInTurn() {
        return Math.min(winsInTurn(positions), winsInTurn(flippedPositions));
    }

    public long getScore() {
        long score = 0;
        for (int y = 0; y< size; y++) {
            for (int x = 0; x< size; x++) {
                if (positions[y][x] > winsInTurn) {
                    score += numbers[y][x];
                }
            }
        }
        return score;
    }

    private int winsInTurn(int[][] values) {
        //noinspection OptionalGetWithoutIsPresent
        return Arrays.stream(values).mapToInt(this::max).min().getAsInt();
    }

    private int max(int[] array) {
        //noinspection OptionalGetWithoutIsPresent
        return Arrays.stream(array).max().getAsInt();
    }
}
