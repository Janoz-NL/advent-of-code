package com.janoz.aoc.y2022.day5;

import com.janoz.aoc.InputProcessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public class Day05 {

    public static void main(String[] args) throws IOException {
        String file = "inputs/2022/day05.txt";
        System.out.println(solve(file));
    }

    static String[] stacks1;
    static String[] stacks2;

    static String solve(String file) throws IOException {
        BufferedReader reader = InputProcessor.getReaderFromResource(file);
        stacks1 = readStacks(reader);
        stacks2 = new String[9];
        System.arraycopy(stacks1,0,stacks2,0,9);

        new InputProcessor<>(reader, s-> {
            int[] move = new int[3];
            String[] parts = s.split(" ");
            for (int i=0; i<3; i++) {
                move[i] = Integer.parseInt(parts[i*2 + 1]);
            }
            return move;
        }).forEach(move -> {
            move1(move[0],move[1],move[2]);
            move2(move[0],move[1],move[2]);
        });

        return getTops(stacks1) + "\n" + getTops(stacks2);
    }

    static void move1(int amount, int from, int to) {
        for (int i=0; i<amount; i++) {
            stacks1[to-1] = stacks1[from-1].charAt(i) + stacks1[to-1];
        }
        stacks1[from-1] = stacks1[from-1].substring(amount);
    }

    static void move2(int amount, int from, int to) {
        stacks2[to-1] = stacks2[from-1].substring(0,amount) + stacks2[to-1];
        stacks2[from-1] = stacks2[from-1].substring(amount);
    }

    static String[] readStacks(BufferedReader reader) throws IOException {
        String[] stacks = new String[9];
        Arrays.fill(stacks,"");
        for (String line=reader.readLine(); !line.startsWith(" 1"); line = reader.readLine()) {
            int j = 0;
            for (int i = 1; i < line.length(); i += 4) {
                if (!(line.charAt(i) == ' ')) {
                    stacks[j] = stacks[j] + line.charAt(i);
                }
                j++;
            }
        }
        reader.readLine();
        return stacks;
    }

    private static String getTops(String[] stacks) {
        StringBuilder result = new StringBuilder();
        for (String stack : stacks) {
            if (stack.length() > 0) {
                result.append(stack.charAt(0));
            }
        }
        return result.toString();
    }

}
