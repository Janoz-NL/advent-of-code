package com.janoz.aoc.y2015.day13;

import com.janoz.aoc.collections.CollectionUtils;
import com.janoz.aoc.collections.TupleKeyMap;
import com.janoz.aoc.input.AocInput;

import java.util.HashSet;
import java.util.Set;

public class Day13 {

    public static void main(String[] args) {
        solve(AocInput.of(2015,13));
    }

    static void solve(AocInput<String> input) {
        input.stream().forEach(Day13::parse);
        String first = persons.iterator().next();
        System.out.println("Part 1: " + happyCircle(first,first,CollectionUtils.newSetWithout(persons,first)));
        for (String person:persons) {
            happiness.put(person,"me",0);
        }
        System.out.println("Part 2: " + happyCircle("me", "me", persons));

    }

    static long happyCircle(String first, String last, Set<String> others) {
        if (others.isEmpty()) {
            return happiness.get(first, last);
        } else {
            long max = Integer.MIN_VALUE;
            for (String other : others) {
                max = Math.max(max, happiness.get(other, last) + happyCircle(first,other, CollectionUtils.newSetWithout(others,other)));
            }
            return max;
        }
    }

    static TupleKeyMap<String, Integer> happiness = new TupleKeyMap<>();
    static Set<String> persons = new HashSet<>();

    static void parse(String s) {
        String[] parts = s.split("[ .]");
        int score = Integer.parseInt(parts[3]);
        if (parts[2].equals("lose")) score *= -1;
        happiness.merge(parts[0],parts[10],score, Integer::sum);
        persons.add(parts[0]);
    }
}
