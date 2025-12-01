package com.janoz.aoc.y2015.day24;

import com.janoz.aoc.input.AocInput;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Day24 {


    public static void main(String[] args) {
        solve(AocInput.of(2015,24));
    }

    static void solve(AocInput<String> input) {
        List<Integer> packages = input.addMapper(Integer::valueOf).asList();
        int weight = sum(packages)/3;
        Integer rootPackage = packages.get(0);

        List<Set<Integer>> firstSets = getSubSets(packages.subList(1,packages.size()), weight - rootPackage);
        firstSets.forEach(l -> l.add(rootPackage));

        long result = Long.MAX_VALUE;

        for (Set<Integer> set1 : firstSets) {
            List<Integer> rest = new ArrayList<>();
            packages.stream().filter(i -> !set1.contains(i)).forEach(rest::add);

            for (Set<Integer> set2 : getSubSets(rest, weight)) {
                Set<Integer> set3 = new HashSet<>();
                rest.stream().filter(i -> !set2.contains(i)).forEach(set3::add);

                long quantum = quantum(smallest(List.of(set1, set2, set3)));
                result = Math.min(result, quantum);
            }

        }

        System.out.println("Part 1: " + result);

    }


    static List<Set<Integer>> getSubSets(List<Integer> all, int weight) {
        if (weight < 0) return Collections.emptyList();
        List<Set<Integer>> results = new ArrayList<>();
        for (int i = 0; i<all.size()-1; i++) {
            Integer current = all.get(i);
            if (current == weight) {
                results.add(new HashSet<>(List.of(current)));
            } else {
                for (Set<Integer> subset : getSubSets(all.subList(i+1,all.size()), weight-current)) {
                    subset.add(current);
                    results.add(subset);
                }
                results.addAll(getSubSets(all.subList(i+1,all.size()), weight));
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
