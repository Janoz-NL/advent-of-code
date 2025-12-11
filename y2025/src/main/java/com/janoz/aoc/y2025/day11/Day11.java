package com.janoz.aoc.y2025.day11;

import com.janoz.aoc.StopWatch;
import com.janoz.aoc.collections.AlwaysHashMap;
import com.janoz.aoc.collections.LongTuple;
import com.janoz.aoc.input.AocInput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day11 {

    public static void main(String[] args) {
        StopWatch.start();
        LongTuple result = solve(AocInput.of(2025,11));
        StopWatch.stopPrint();
        printResult(result);
    }

    static LongTuple solve(AocInput<String> input) {
        input.stream().forEach(Day11::parse);
        long answer1 = nrOfPaths(nodes.get("you"), nodes.get("out"));
        pathCache.clear();
        long d2f = nrOfPaths(nodes.get("dac"), nodes.get("fft"));
        pathCache.clear();
        long f2d = nrOfPaths(nodes.get("fft"), nodes.get("dac"));
        pathCache.clear();
        long answer2 = -1;
        if (d2f == 0) {
            answer2 = f2d * nrOfPaths(nodes.get("svr"), nodes.get("fft"));
            pathCache.clear();
            answer2 *= nrOfPaths(nodes.get("dac"), nodes.get("out"));
        } else {
            answer2 = d2f * nrOfPaths(nodes.get("svr"), nodes.get("dac"));
            pathCache.clear();
            answer2 *= nrOfPaths(nodes.get("fft"), nodes.get("out"));
        }
        return new LongTuple(answer1,answer2);
    }

    static Map<Node,Long> pathCache = new HashMap<>();

    static long nrOfPaths(Node start, Node end) {
        if (pathCache.containsKey(start)) return pathCache.get(start);
        if (start == end) return 1;
        long paths = start.next.stream().mapToLong(n -> nrOfPaths(n, end)).sum();
        pathCache.put(start,paths);
        return paths;
    }
    static void printResult(LongTuple results) {
        System.out.printf("Part 1 : %d\nPart 2 : %d",results.getLeft(), results.getRight());
    }

    static Map<String, Node> nodes = new AlwaysHashMap<>(Node::new);

    static void parse(String line) {
        String[] items = line.split(":? +");
        Node n = nodes.get(items[0]);
        for (int i=1;i<items.length;i++) {
            Node next = nodes.get(items[i]);
            n.next.add(next);
        }
    }

    static class Node {
        final String name;
        final List<Node> next = new ArrayList<>();

        Node(String name) {
            this.name = name;
        }
    }
}
