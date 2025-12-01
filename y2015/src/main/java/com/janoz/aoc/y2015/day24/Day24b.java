package com.janoz.aoc.y2015.day24;

import com.janoz.aoc.collections.CollectionUtils;
import com.janoz.aoc.input.AocInput;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day24b {


    public static void main(String[] args) {
        solve(AocInput.of(2015,24));
    }

    static void solve(AocInput<String> input) {
        List<Integer> packages = input.addMapper(Integer::valueOf).asList();
        int weight = sum(packages)/3;
        List<Set<Integer>> sets = getSets(packages, weight);
//        List<Set<Integer>> sets = getSmallestSets(packages, weight, packages.size()).stream()
//                .filter(s -> {
//            Set<Integer> others = new HashSet<>();
//            packages.stream().filter(i -> !s.contains(i)).forEach(others::add);
//            return canBeSplit(others, weight);
//        })
//                .toList();
        long answer = sets.stream().mapToLong(Day24b::quantum).min().getAsLong();

        System.out.println("Part 1: " + answer);

    }


    static List<Set<Integer>> getSets(List<Integer> packages, int weight) {
        if (weight < 0 || packages.isEmpty()) return Collections.emptyList();

        Integer last = packages.get(packages.size()-1);
        if (weight == last) {
            List<Set<Integer>> result = new ArrayList<>();
            result.add(new HashSet<>());
            result.get(0).add(last);
        }
        List<Set<Integer>> with = getSets(packages.subList(0,packages.size()-1), weight-last);
        with.forEach(s -> s.add(last));
        List<Set<Integer>> without = getSets(packages.subList(0,packages.size()-1), weight);

        int minsize = Math.min(
                with.stream().mapToInt(Set::size).min().orElse(Integer.MAX_VALUE),
                without.stream().mapToInt(Set::size).min().orElse(Integer.MAX_VALUE));

        return Stream.concat(with.stream(), without.stream()).filter(s -> s.size() == minsize).toList();
    }

//    static boolean canBeSplit(Set<Integer> all, int weight) {
//        if (weight == 0) return true;
//        if (weight < 0) return false;
//        if (all.isEmpty()) return false;
//        Integer i = all.iterator().next();
//        Set<Integer> next = CollectionUtils.newSetWithout(all,i);
//        return canBeSplit(next, weight) || canBeSplit(next, weight - i);
//    }


    static List<Set<Integer>> getSmallestSets(List<Integer> all, int weight, int maxSize) {
        if (maxSize == 0) return Collections.emptyList();
        if (weight < 0) return Collections.emptyList();

        List<Set<Integer>> results = new ArrayList<>();
        for (int i = 0; i<all.size()-1; i++) {
            Integer current = all.get(i);
            if (current == weight) {
                if (maxSize > 1) results.clear();
                results.add(new HashSet<>(List.of(current)));
                maxSize = 1;
            } else {
                for (Set<Integer> subset : getSmallestSets(all.subList(i+1,all.size()), weight-current, maxSize-1)) {
                    subset.add(current);
                    if (subset.size() < maxSize) {
                        results.clear();
                        maxSize = subset.size();
                    }
                    if (subset.size() == maxSize) {
                        results.add(subset);
                    }
                }
                for (Set<Integer> subset : getSmallestSets(all.subList(i+1,all.size()), weight, maxSize)) {
                    if (subset.size() < maxSize) {
                        results.clear();
                        maxSize = subset.size();
                    }
                    if (subset.size() == maxSize) {
                        results.add(subset);
                    }
                }
            }
        }
        return results;
    }

    static int sum(Collection<Integer> collection) {
        return collection.stream().mapToInt(Integer::intValue).sum();
    }

    static long quantum(Collection<Integer> collection) {
        return collection.stream().mapToLong(Integer::longValue).reduce(1, (a, b) -> a * b);
    }




    /**
     *
     * @param sets collection of sets
     * @return smallest. When 2 sets have the same size, return the onw with the smallest entanglement
     */
    static Set<Integer> smallest(Collection<Set<Integer>> sets) {
        Iterator<Set<Integer>> itt = sets.iterator();
        Set<Integer> result = itt.next();
        while (itt.hasNext()) {
            Set<Integer> candidate = itt.next();
            if (candidate.size() < result.size()) {
                result = candidate;
            } else if (candidate.size() == result.size()) {
                if (quantum(candidate) < quantum(result)) {
                    result = candidate;
                }
            }
        }
        return result;
    }





}
