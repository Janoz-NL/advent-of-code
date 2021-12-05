package com.janoz.aoc.y2021.day4;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

import static com.janoz.aoc.MatrixUtils.*;

public class Board {

    final int size;

    int[][] numbers;
    int[][] positions;

    int winsInTurn;

    public Board(BufferedReader input, int[] positionMap, int size) throws IOException {
        this.size = size;
        numbers = read(input,size);
        positions = map(numbers,positionMap);
        winsInTurn = Math.min(
                winsInTurn(positions), //find first bingo in rows
                winsInTurn(transpose(positions))); //find first bingo in columns
    }

    //find first row that gives bingo
    private int winsInTurn(int[][] values) {
        //noinspection OptionalGetWithoutIsPresent
        return Arrays.stream(values).mapToInt(this::max).min().getAsInt();
    }

    //find last item which gives bingo
    private int max(int[] array) {
        //noinspection OptionalGetWithoutIsPresent
        return Arrays.stream(array).max().getAsInt();
    }

    public long getScore() {
        long score = 0;
        for (int row = 0; row< size; row++) {
            for (int col = 0; col< size; col++) {
                if (positions[row][col] > winsInTurn) {
                    score += numbers[row][col];
                }
            }
        }
        return score;
    }

}
