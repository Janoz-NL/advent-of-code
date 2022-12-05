package com.janoz.aoc.y2022.day2;

import com.janoz.aoc.InputProcessor;

import java.io.IOException;
import java.net.URISyntaxException;

public class Day2Lazy {

    static final int[] p1 = new int[] {4, 1, 7, 8, 5, 2, 3, 9, 6};
    static final int[] p2 = new int[] {3, 1, 2, 4, 5, 6, 8, 9, 7};

    public static void main(String[] args) throws IOException, URISyntaxException {
        String file = "inputs/2022/day02.txt";
        System.out.println(InputProcessor.asStream(file, s -> p1[(s.charAt(0)-'A') + 3 * (s.charAt(2) - 'X')]).reduce(Integer::sum).get());
        System.out.println(InputProcessor.asStream(file, s -> p2[(s.charAt(0)-'A') + 3 * (s.charAt(2) - 'X')]).reduce(Integer::sum).get());
    }
}
