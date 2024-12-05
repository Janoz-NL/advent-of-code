package com.janoz.aoc.y2024.day5;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;


import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.collections.AlwaysHashMap;
import com.janoz.aoc.math.IntMatrixUtils;

public class Day5 {
    final static AlwaysHashMap<Integer, Set<Integer>> beforeRule = new AlwaysHashMap<>(() -> new HashSet<>());
    final static AlwaysHashMap<Integer, Set<Integer>> afterRule = new AlwaysHashMap<>(() -> new HashSet<>());
    static final Comparator<Integer> pageSorter = (a, b) -> {
        if (beforeRule.get(a).contains(b)) return -1;
        if (afterRule.get(a).contains(b)) return 1;
        return 0;
    };
    final static List<int[]> wrongs = new ArrayList<>();

    public static void main(String[] args) {
        BufferedReader reader = InputProcessor.getReaderFromResource("inputs/2024/day05.txt");
        new InputProcessor<>(reader, s -> s.split("\\|")).stream().forEach(sa -> {
            int i1 = Integer.parseInt(sa[0]);
            int i2 = Integer.parseInt(sa[1]);
            beforeRule.get(i1).add(i2);
            afterRule.get(i2).add(i1);
        });
        System.out.println(new InputProcessor<>(reader, s -> IntMatrixUtils.readLine(s,",")).stream().mapToInt(Day5::check).sum());
        System.out.println(wrongs.stream().mapToInt(Day5::sort).sum());
    }

    private static int check(int[] list) {
        for (int i=1; i<list.length; i++){
            for (int j=0; j<i; j++) {
                if (beforeRule.get((list[i])).contains(list[j])) {
                    wrongs.add(list);
                    return 0;
                }
            }
        }
        return list[list.length/2];
    }

    private static int sort(int[] list) {
        SortedSet<Integer> sorted = new TreeSet<>(pageSorter);
        Arrays.stream(list).forEach(sorted::add);
        if (list.length != sorted.size()) {
            throw new RuntimeException("Some values gone");
        }
        Iterator<Integer> it = sorted.iterator();
        for (int i=0; i<list.length/2; i++) {
            it.next();
        }
        return it.next();
    }
}
