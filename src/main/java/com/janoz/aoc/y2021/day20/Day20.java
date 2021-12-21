package com.janoz.aoc.y2021.day20;

import com.janoz.aoc.InputProcessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Day20 {

    static String lookupMap;
    static char infinite = '.';

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = InputProcessor.getReaderFromResource("inputs/day20.txt");
        lookupMap = bufferedReader.readLine();
        bufferedReader.readLine();
        char[][] map = readMap(bufferedReader.lines().collect(Collectors.toList()));
        for (int i=0; i<50;i++) {
            map = enhance(map);
        }
        System.out.println(countPixels(map));
    }

    static char[][] enhance(char[][] input) {
        char[][] result = new char[input.length+2][];
        for (int y=0;y<input.length+2;y++) {
            result[y] = new char[input[0].length+2];
            for (int x=0; x<input[0].length+2; x++) {
                result[y][x] = lookupMap.charAt(getMapIndex(input,x-1,y-1));
            }
        }
        if (infinite == '.') infinite = lookupMap.charAt(0);
        else infinite = lookupMap.charAt(511);
        return result;
    }

    static int getMapIndex(char[][] map, int x, int y) {
        return Integer.parseInt(""+
                map(getAt(map,x-1,y-1)) +
                map(getAt(map,x,y-1)) +
                map(getAt(map,x+1,y-1)) +
                map(getAt(map,x-1,y)) +
                map(getAt(map,x,y)) +
                map(getAt(map,x+1,y)) +
                map(getAt(map,x-1,y+1)) +
                map(getAt(map,x,y+1)) +
                map(getAt(map,x+1,y+1)),2);
    }

    static char map(char in) {
        return in=='#'?'1':'0';
    }

    static char getAt(char[][] map, int x, int y) {
        if (x<0 || y<0 || x>=map[0].length || y>=map.length) return infinite;
        return map[y][x];
    }

    static long countPixels(char[][] map) {
        long result = 0;
        for (char[] row : map) {
            for (char c: row) {
                if (c == '#') result++;
            }
        }
        return result;
    }

    static char[][] readMap(List<String> lines) {
        char[][] result = new char[lines.size()][];
        for (int y=0; y< lines.size(); y++) {
            result[y] = lines.get(y).toCharArray();
        }
        return result;
    }

    static void printMap(char[][] map) {
        for (char[] row : map) {
            for (char c: row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }
}
