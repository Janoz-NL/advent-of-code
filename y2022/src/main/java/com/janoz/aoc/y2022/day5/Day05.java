package com.janoz.aoc.y2022.day5;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.StopWatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class Day05 {

    public static void main(String[] args) throws IOException {
        StopWatch.start();
        String file = "inputs/2022/day05.txt";
        System.out.println(solve(file, (move -> move1(move[0],move[1],move[2]))));
        StopWatch.stopPrint();
    }

    static Stack[] stacks;

    static String solve(String file, Consumer<int[]> mover) throws IOException {
        BufferedReader reader = InputProcessor.getReaderFromResource(file);
        stacks = readStacks(reader);

        new InputProcessor<>(reader, s-> {
            int[] move = new int[3];
            String[] parts = s.split(" ");
            for (int i=0; i<3; i++) {
                move[i] = Integer.parseInt(parts[i*2 + 1]);
            }
            return move;
        }).forEach(mover);

        return getTops(stacks);
    }

    static void move1(int amount, int from, int to) {
        for (int i=0; i<amount; i++) {
            stacks[to-1].add(stacks[from-1].get());
        }
    }

    static void move2(int amount, int from, int to) {
        stacks[to-1].add(stacks[from-1].get(amount));
    }

    static Stack[] readStacks(BufferedReader reader) throws IOException {
        Stack[] stacks = new Stack[9];
        IntStream.range(0,9).forEach(i -> stacks[i] = new Stack());
        for (String line=reader.readLine(); !line.startsWith(" 1"); line = reader.readLine()) {
            int j = 0;
            for (int i = 1; i < line.length(); i += 4) {
                if (!(line.charAt(i) == ' ')) {
                    stacks[j].append(new Crate(line.charAt(i)));
                }
                j++;
            }
        }
        reader.readLine();
        return stacks;
    }

    private static String getTops(Stack[] stacks) {
        StringBuilder result = new StringBuilder();
        for (Stack stack : stacks) {
            if (stack.top != null)
                result.append(stack.top.c);
        }
        return result.toString();
    }
}
