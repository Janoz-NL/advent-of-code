package com.janoz.aoc.y2022.day6;

import java.util.Arrays;

public class Day06 {

    static int[] chars = new int[26];
    static int dupes;

    public static void main(String[] args) {
        System.out.println(solve(args[1], Integer.parseInt(args[0])));
    }

    private static void init() {
        Arrays.fill(chars,0);
        dupes = 0;
    }

    private static void add(char c) {
        int i = c - 'a';
        chars[i]++;
        if (chars[i]==2) dupes++;
    }

    private static void remove(char c) {
        int i = c - 'a';
        chars[i]--;
        if (chars[i]==1) dupes--;
    }

    public static int solve(String input, int headersize) {
        init();
        for (int i=0; i<headersize; i++) {
            add(input.charAt(i));
        }
        int pos = headersize;
        while (dupes > 0) {
            remove(input.charAt(pos-headersize));
            add(input.charAt(pos));
            pos++;
        }
        return pos;
    }
}
