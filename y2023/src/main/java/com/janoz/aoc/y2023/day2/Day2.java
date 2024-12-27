package com.janoz.aoc.y2023.day2;

import java.util.Arrays;
import java.util.List;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import com.janoz.aoc.InputProcessor;

public class Day2 {


    public static void main(String[] args) {
        System.out.println(solve("inputs/2023/day02.txt"));
        System.out.println(solve2("inputs/2023/day02.txt"));

    }

    static int solve(String file) {
        return InputProcessor.asStream(file, Round::new).filter(Round::isValid).mapToInt(r -> r.number).sum();
    }

    static int solve2(String file) {
        return InputProcessor.asStream(file, Round::new).mapToInt(Round::power).sum();
    }

    static class Round {


        Round(String line) {
            String[] parts = line.split(":");
            number = Integer.parseInt(parts[0].split(" ")[1]);
            draws = Arrays.stream(parts[1].split(";")).map(Draw::new).collect(Collectors.toList());
        }
        int number;
        List<Draw> draws;

        boolean isValid() {
            return draws.stream().allMatch(Draw::isValid);
        }

        int power() {
            return
                    max(d -> d.red) *
                    max(d -> d.green) *
                    max(d -> d.blue);
        }

        private int max(ToIntFunction<Draw> color) {
            return draws.stream().mapToInt(color).max().getAsInt();
        }
    }

}
