package com.janoz.aoc.y2015.day9;

import com.janoz.aoc.collections.TupleKeyMap;
import com.janoz.aoc.input.AocInput;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.janoz.aoc.collections.CollectionUtils.newListWith;
import static com.janoz.aoc.collections.CollectionUtils.newSetWithout;
import static java.util.Collections.singletonList;

public class Day9 {

    public static void main(String[] args) {
        solve(AocInput.of(2015,9));
    }

    static TupleKeyMap<String,Long> distances = new TupleKeyMap<>();
    static Set<String> cities = new HashSet<>();

    static void solve(AocInput<String> input) {
        input.stream().forEach(Day9::parseDistance);
        long[] extrema = distances(cities);
        System.out.println("Part 1: " + extrema[0]);
        System.out.println("Part 2: " + extrema[1]);
    }

    static long[] distances(Set<String> cities) {
        String first = cities.iterator().next();
        return distances(Integer.MAX_VALUE, 0, 0,
                singletonList(first),
                newSetWithout(cities, first));
    }

    static long[] distances(long min, long max, long total, List<String> head, Set<String> tail) {
        if (tail.isEmpty()) {
            long distance = distances.get(head.get(0),head.get(head.size()-1));
            max = Math.max(max, distance);
            min = Math.min(min, distance);
            total = total + distance;
            return new long[]{total - max, total - min};
        } else {
            long resultMin = Integer.MAX_VALUE;
            long resultMax = 0;
            for (String city : tail) {
                long distance = distances.get(city,head.get(head.size()-1));
                long[] extrema = distances(
                        Math.min(min, distance),
                        Math.max(max, distance),
                        total + distance,
                        newListWith(head,city),
                        newSetWithout(tail,city));
                resultMin = Math.min(resultMin, extrema[0]);
                resultMax = Math.max(resultMax, extrema[1]);
            }
            return new long[]{resultMin, resultMax};
        }
    }

    private static void parseDistance(String config) {
        String[] parts = config.split(" ");
        cities.add(parts[0]);
        cities.add(parts[2]);
        distances.put(parts[0], parts[2], Long.parseLong(parts[4]));
    }
}