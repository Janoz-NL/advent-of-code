package com.janoz.aoc.y2022.day8;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.StopWatch;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day08 {


    static int[][] trees = new int[100][100];
    static int width;
    static int height;




    public static void main(String[] args) throws IOException {
        StopWatch.start();
        String file = "inputs/2022/day08.txt";

        readTrees(file);
        System.out.println(part1());
        System.out.println(part2());

        StopWatch.stopPrint();
    }


    private static long part2() {
        long answer = 0;
        for (int i=0;i<height; i++) {
            for (int j=width-1; j>=0; j--) {
                answer = Math.max(determineScenicScore(j,i), answer);
            }

        }
        return answer;
    }

    private static long determineScenicScore(int j, int i) {
        long top = 0;
        int di=i-1;
        while (di >=0 && trees[j][di] < trees[j][i]) {
            di--;
            top++;
        }
        if (di>=0) top++;

        long bottom = 0;
        di=i+1;
        while (di <height && trees[j][di] < trees[j][i]) {
            di++;
            bottom++;
        }
        if (di <height) bottom++;

        long left = 0;
        int dj=j-1;
        while (dj >=0 && trees[dj][i] < trees[j][i]) {
            dj--;
            left++;
        }
        if (dj>=0) left++;

        long right = 0;
        dj=j+1;
        while (dj < width && trees[dj][i] < trees[j][i]) {
            dj++;
            right++;
        }
        if (dj <width) right++;

        return top * bottom * left * right;
    }

    private static int part1() {
        boolean[][]  visible = new boolean[width][height];

        int max;
        //doleft
        for (int i=0;i<height; i++) {
            max = -1;
            for (int j=0; j<width; j++) {
                if (trees[j][i] > max) {
                    visible[j][i] = true;
                    max = trees[j][i];
                }
            }
        }

        //doRight
        for (int i=0;i<height; i++) {
            max = -1;
            for (int j=width-1; j>=0; j--) {
                if (trees[j][i] > max) {
                    visible[j][i] = true;
                    max = trees[j][i];
                }
            }
        }

        //doTop
        for (int j=0; j<width; j++) {
            max = -1;
            for (int i=0;i<height; i++) {
                if (trees[j][i] > max) {
                    visible[j][i] = true;
                    max = trees[j][i];
                }
            }
        }

        //doBottom
        for (int j=0; j<width; j++) {
            max = -1;
            for (int i=height-1;i>=0; i--) {
                if (trees[j][i] > max) {
                    visible[j][i] = true;
                    max = trees[j][i];
                }
            }
        }

        //count
        int total = 0;
        for (int i=0;i<height; i++) {
            for (int j = 0; j < width; j++) {
                if (visible[j][i]) {
                    total++;
                }
            }
        }
        return total;
    }

    static void readTrees(String file) {
        List<String> input = InputProcessor.asStream(file).collect(Collectors.toList());
        width = input.size();
        height = input.get(0).length();
        trees = new int[width][height];

        for (int r=0; r < height; r++) {
            for (int c=0; c<width; c++) {
                trees[c][r]= input.get(r).charAt(c);
            }
        }
    }
}
