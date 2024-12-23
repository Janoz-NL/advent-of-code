package com.janoz.aoc.y2024.day23;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.collections.AlwaysHashMap;
import com.janoz.aoc.graphs.Edge;
import com.janoz.aoc.graphs.Node;

public class Day23 {

    static Map<String, Node<String>> computers = new AlwaysHashMap<>((k) -> new Node<>(k));

    public static void main(String[] args) {
        InputProcessor.asStream("inputs/2024/day23.txt").forEach(Day23::read);
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    static String part2() {
        Set<Set<Node<String>>> connectedSets = new HashSet<>();
        computers.values().forEach(computer -> {
            Set<Set<Node<String>>> newSets = new HashSet<>();
            newSets.add(Collections.singleton(computer));
            for (Set<Node<String>> set : connectedSets) {
                if (computer.reachable().containsAll(set)) {
                    newSets.add(newSet(set,computer));
                }
            }
            connectedSets.addAll(newSets);
        });
        Set<Node<String>> max = Collections.emptySet();
        for (Set<Node<String>> set:connectedSets) {
            if (set.size() > max.size()) {
                max = set;
            }
        }
        return networkToString(max);
    }

    static <T> Set<T> newSet(Set<T> set, T element) {
        Set<T> result = new HashSet<>(set);
        result.add(element);
        return result;
    }
    static int part1() {
        Set<String> answers = new HashSet<>();
        computers.values().stream()
                .filter(c -> c.getData().charAt(0) == 't')
                .forEach(computer ->
                    computer.reachable()
                            .forEach(reachable1 ->
                                reachable1.reachable()
                                        .stream()
                                        .filter(c -> c.getTo(computer) != null)
                                        .forEach(c -> answers.add(networkToString(computer, reachable1, c)))));
        return answers.size();
    }

    @SafeVarargs
    static String networkToString(Node<String>... computers) {
        return networkToString(Arrays.asList(computers));
    }
    static String networkToString(Collection<Node<String>> network) {
        return network.stream().map(Node::getData).sorted().collect(Collectors.joining(","));
    }

    static void read(String line) {
        String c1 = line.substring(0,2);
        String c2 = line.substring(3);
        Node<String> n1 = computers.get(c1);
        Node<String> n2 = computers.get(c2);
        new Edge(n1,n2);
        new Edge(n2,n1);
    }
}
