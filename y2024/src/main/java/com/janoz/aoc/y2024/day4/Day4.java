package com.janoz.aoc.y2024.day4;

import java.util.List;

import com.janoz.aoc.input.AocInput;

public class Day4 {

    public static void main(String[] args) {
        List<String> data = AocInput.of(2024,4).asList();
        int width = data.get(0).length();
        int height = data.size();

        int count = solve2(data, width, height);
        System.out.println(count);
    }

    static int solve2(List<String> data, int width, int height) {
        int count = 0;
        for (int x = 1; x< width-1; x++) {
            for (int y = 1; y< height-1; y++) {
                if (data.get(y).charAt(x) == 'A') {
                    char tl = data.get(y-1).charAt(x-1);
                    char tr = data.get(y-1).charAt(x+1);
                    char bl = data.get(y+1).charAt(x-1);
                    char br = data.get(y+1).charAt(x+1);
                    if (
                            ((tl == 'M' && br == 'S') || (tl == 'S' && br == 'M')) &&
                            ((tr == 'M' && bl == 'S') || (tr == 'S' && bl == 'M'))) {
                                count++;
                    }
                }
            }
        }
        return count;
    }




    static int solve1(List<String> data, int width, int height) {
        int count = 0;
        for (int x = 0; x< width; x++) {
            for (int y = 0; y< height; y++) {
                for (int dx=-1; dx < 2; dx++) {
                    for (int dy = -1; dy < 2; dy++) {
                        try {
                            if (
                                    (data.get(y).charAt(x) == 'X') &&
                                            (data.get(y + dy).charAt(x + dx) == 'M') &&
                                            (data.get(y + dy + dy).charAt(x + dx + dx) == 'A') &&
                                            (data.get(y + dy + dy + dy).charAt(x + dx + dx + dx) == 'S')
                            ) {
                                count++;
                            }
                        } catch (IndexOutOfBoundsException ioobe) {

                        }
                    }
                }
            }
        }
        return count;
    }

}
