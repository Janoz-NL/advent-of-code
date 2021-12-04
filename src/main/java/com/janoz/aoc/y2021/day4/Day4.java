package com.janoz.aoc.y2021.day4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Objects;

public class Day4 {

    public static void main(String[] args) throws IOException {
        Day4 day4 = new Day4();
        day4.init("inputs/day4.txt");
        System.out.println(day4.getScore(day4.winner));
        System.out.println(day4.getScore(day4.loser));
    }

    int[] positionMap = new int[100];
    int[] numbers;

    Board winner;
    Board loser;

    public void init(String file) throws IOException {
        BufferedReader input = openStream(file);
        readNumbers(input);
        Board board;
        int firstBingoInTurn = 101;
        int lastBingoInTurn = 0;
        while ((board = readBoard(input)) != null) {
            if (board.winsInTurn < firstBingoInTurn) {
                firstBingoInTurn = board.winsInTurn;
                winner = board;
            }
            if (board.winsInTurn > lastBingoInTurn) {
                lastBingoInTurn = board.winsInTurn;
                loser = board;
            }
        }
    }

    public int getScore(Board b) {
        return b.getScore()*numbers[b.winsInTurn];
    }

    private BufferedReader openStream(String file) {
        return new BufferedReader(new InputStreamReader(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(file))));
    }

    private void readNumbers(BufferedReader input) throws IOException {
         numbers = Arrays.stream(input.readLine().split(",")).mapToInt(Integer::parseInt).toArray();
         for (int i=0;i<numbers.length;i++) {
             positionMap[numbers[i]] = i;
         }
    }

    private Board readBoard(BufferedReader input) throws IOException {
        if (input.readLine() == null) return null;
        return new Board(input, positionMap);
    }

}
